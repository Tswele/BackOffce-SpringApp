package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import za.co.wirecard.channel.backoffice.dto.models.requests.ForgotPassword;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserLoginEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserPasswordHistoryEntity;
import za.co.wirecard.channel.backoffice.entities.UnverifiedBackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.Token;
import za.co.wirecard.channel.backoffice.models.TokenVerify;
import za.co.wirecard.channel.backoffice.models.email.EmailNotification;
import za.co.wirecard.channel.backoffice.mq.SendEmail;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserLoginRepository;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserPasswordHistoryRepository;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserRepository;
import za.co.wirecard.channel.backoffice.repositories.UnverifiedBackOfficeUserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Value("${api.oauth.url}")
    private String url;
    @Value("${application.external.endpoints.reset-password}")
    private String forgottenPasswordUrl;

    private static final Logger logger = LogManager.getLogger(UserService.class);
    private BackOfficeUserRepository backOfficeUserRepository;
    private final BackOfficeUserLoginRepository backOfficeUserLoginRepository;
    private final UnverifiedBackOfficeUserRepository unverifiedBackOfficeUserRepository;
    private final BackOfficeUserPasswordHistoryRepository backOfficeUserPasswordHistoryRepository;
    private final JavaMailSender javaMailSender;
    private final SendEmail sendEmail;

    public AuthenticationService(BackOfficeUserRepository backOfficeUserRepository, BackOfficeUserLoginRepository backOfficeUserLoginRepository, UnverifiedBackOfficeUserRepository unverifiedBackOfficeUserRepository, BackOfficeUserPasswordHistoryRepository backOfficeUserPasswordHistoryRepository, JavaMailSender javaMailSender, SendEmail sendEmail) {
        this.backOfficeUserRepository = backOfficeUserRepository;
        this.backOfficeUserLoginRepository = backOfficeUserLoginRepository;
        this.unverifiedBackOfficeUserRepository = unverifiedBackOfficeUserRepository;
        this.backOfficeUserPasswordHistoryRepository = backOfficeUserPasswordHistoryRepository;
        this.javaMailSender = javaMailSender;
        this.sendEmail = sendEmail;
    }

    public Token getToken(String username, String password) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // create a map for post parameters

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                // Add query parameter
                .queryParam("grant_type", "password")
                .queryParam("username", username)
                .queryParam("password", password)
                .queryParam("client_id", "back-office")
                .queryParam("client_secret", "secret");

        // send POST request
        ResponseEntity<Token> response = restTemplate.postForEntity(builder.toUriString(), null, Token.class);

        //ResponseEntity<Token> token = restTemplate.exchange(url, HttpMethod.POST, null, Token.class);
        return response.getBody();
    }

    public TokenVerify getTokenVerify() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create a map for post parameters

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                // Add query parameter
                .queryParam("grant_type", "client_credentials")
                .queryParam("client_id", "back-office")
                .queryParam("client_secret", "secret");

        logger.info("URL " + builder);

        // send POST request
        ResponseEntity<TokenVerify> response = restTemplate.postForEntity(builder.toUriString(), null, TokenVerify.class);

        //ResponseEntity<Token> token = restTemplate.exchange(url, HttpMethod.POST, null, Token.class);
        return response.getBody();
    }

    public void createTwoFactorAuthResponse(long userId) {
    }

    @Transactional
    public void forgotPassword(ForgotPassword forgotPassword, HttpServletRequest servletRequest) {
        logger.info("Find user | " + forgotPassword.getUsername());
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository
                .findByEmailIgnoreCase(forgotPassword.getUsername())
                .orElseThrow(() -> new GenericException("No user with that email exists", HttpStatus.NOT_FOUND, "Could not find a user with email " + forgotPassword.getUsername()));
        try {
            checkLockout(getIpAddressFromServletRequest(servletRequest), forgotPassword.getUsername());
            logger.info("Found user | " + backOfficeUserEntity.getId());
            unverifiedBackOfficeUserRepository.deleteAllByBackOfficeUserByUserId(backOfficeUserEntity);
            UnverifiedBackOfficeUserEntity unverifiedBackOfficeUserEntity = new UnverifiedBackOfficeUserEntity(backOfficeUserEntity);
            unverifiedBackOfficeUserRepository.save(unverifiedBackOfficeUserEntity);

            String url = String.format(forgottenPasswordUrl, unverifiedBackOfficeUserEntity.getUuid());
            EmailNotification emailNotification = new EmailNotification(backOfficeUserEntity.getEmail(), "Back Office Reset Password", setResetPasswordEmailTemplate(url, unverifiedBackOfficeUserEntity.getBackOfficeUserByUserId().getFirstName()));
            sendEmail.send(emailNotification);
//            try {
//                twoFAService.sendEmail(emailNotification);
//            } catch (MessagingException e) {
//                throw new GenericException("Something went wrong with sending the email", HttpStatus.INTERNAL_SERVER_ERROR, "Email sending failed");
//            }
        } catch (GenericException e) {
            logger.error(e);
            throw e;
        }
    }

    public String setEmailOtpTemplate(String name, String otp) {
//        return  "<html>" +
//                "<body style=\"font-family: Arial, Helvetica, sans-serif; color: white; background-color: #021C30;\">" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr><p style=\"font-size: 16px; white-space: pre;\"> </p></tr>" +
//                "<tr>" +
//                "<th>" +
//                "<svg xmlns=\"http://www.w3.org/2000/svg\" style=\"width: 100px; height: 100px;\" fill=\"none\" viewBox=\"0 0 24 24\" stroke=\"currentColor\">" +
//                "<path stroke-linecap=\"round\" stroke-linejoin=\"round\" stroke-width=\"2\" d=\"M7 20l4-16m2 16l4-16M6 9h14M4 15h14\"/>" +
//                "</svg>" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr>" +
//                "<th>" +
//                "<h1 style=\"color: white !important;\">Otp</h1>" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr>" +
//                "<th></th>" +
//                "<th width=\"25%\" style=\"\">" +
//                "<div style=\"font-size: 12px; white-space: pre;\"> </div>" +
//                "<div><p style=\"color: white; font-size: 20px; font-weight: 600; border: none;\">" + otp + "</p></div>" +
//                "<div style=\"font-size: 12px; white-space: pre;\"> </div>" +
//                "</th>" +
//                "<th></th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr>" +
//                "<th>" +
//                "<p style=\"font-size: 12px; margin: 0; color: white !important;\">your email address and set your</p>" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr>" +
//                "<th>" +
//                "<p style=\"font-size: 12px; margin: 0; color: white !important;\">password</p>" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table>" +
//                "<tr><p style=\"font-size: 10px; white-space: pre;\"> </p></tr>" +
//                "</table>" +
//                "</div>" +
//                "</body>" +
//                "</html>";
        String description = "Hi " + name + "<br><br>" +
                "You are receiving this email because an otp has been generated for your account. <br><br>" +
                "Please find the OTP below:";
        String title = "Adumo Online Back Office OTP";
        return "<!DOCTYPE html>" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "<meta name=\"viewport\" content=\"initial-scale=1.0\">" +
                "<meta name=\"format-detection\" content=\"telephone=no\">" +
                "<title>" + title + "</title>" +
                "<style type=\"text/css\">" +
                "body{ margin: 0; padding: 0; }" +
                "img{ border: 0px; display: block; }" +
                ".socialLinks{ font-size: 6px; }" +
                ".socialLinks a{" +
                "display: inline-block;" +
                "}" +
                ".long-text p{ margin: 1em 0px; }" +
                ".long-text p:last-child{ margin-bottom: 0px; }" +
                ".long-text p:first-child{ margin-top: 0px; }" +
                "</style>" +
                "<style type=\"text/css\">" +
                "/* yahoo, hotmail */" +
                ".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{ line-height: 100%; }" +
                ".yshortcuts a{ border-bottom: none !important; }" +
                ".vb-outer{ min-width: 0 !important; }" +
                ".RMsgBdy, .ExternalClass{" +
                "width: 100%;" +
                "background-color: #ffffff;" +
                "background-color: #ffffff}" +
                "/* outlook/office365 add buttons outside not-linked images and safari have 2px margin */" +
                "[o365] button{ margin: 0 !important; }" +
                "/* outlook */" +
                "table{ mso-table-rspace: 0pt; mso-table-lspace: 0pt; }" +
                "#outlook a{ padding: 0; }" +
                "img{ outline: none; text-decoration: none; border: none; -ms-interpolation-mode: bicubic; }" +
                "a img{ border: none; }" +
                "@media screen and (max-width: 600px) {" +
                "table.vb-container, table.vb-row{" +
                "width: 95% !important;" +
                "}" +
                ".mobile-hide{ display: none !important; }" +
                ".mobile-textcenter{ text-align: center !important; }" +
                ".mobile-full{ " +
                "width: 100% !important;" +
                "max-width: none !important;" +
                "}" +
                "}" +
                "/* previously used also screen and (max-device-width: 600px) but Yahoo Mail doesn't support multiple queries */" +
                "</style>" +
                "<style type=\"text/css\">" +
                "" +
                "#ko_sideArticleBlock_8 .links-color a, #ko_sideArticleBlock_8 .links-color a:link, #ko_sideArticleBlock_8 .links-color a:visited, #ko_sideArticleBlock_8 .links-color a:hover{" +
                "color: #ffffff;" +
                "color: #000000;" +
                "text-decoration: underline" +
                "}" +
                "#ko_footerBlock_2 .links-color a, #ko_footerBlock_2 .links-color a:link, #ko_footerBlock_2 .links-color a:visited, #ko_footerBlock_2 .links-color a:hover{" +
                "color: #cccccc;" +
                "color: #cccccc;" +
                "text-decoration: underline" +
                "}" +
                "</style>" +
                "</head>" +
                "<!--[if !(gte mso 16)]-->" +
                "<body bgcolor=\"#ffffff\" text=\"#919191\" alink=\"#cccccc\" vlink=\"#cccccc\" style=\"margin: 0; padding: 0; background-color: #ffffff; color: #919191;\"><!--<![endif]--><center>" +
                "<table role=\"presentation\" class=\"vb-outer\" width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"background-color: #ffffff;\" id=\"ko_sideArticleBlock_8\">" +
                "<tbody><tr><td class=\"vb-outer\" align=\"center\" valign=\"top\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\">" +
                "<!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--" +
                "--><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" cellpadding=\"0\" border=\"1px solid #b9b9b9;\" border-collapse=\"separate\" border-radius=\"4px\" margin-top=\"12px\" margin-bottom=\"0px\" bgcolor=\"#ffffff\" cellspacing=\"9\" width=\"570\" class=\"vb-row\" style=\"border: 1px solid #b9b9b9; margin-top: 12px; margin-bottom: 0px; border-collapse: separate; width: 100%; background-color: #ffffff; border-top-left-radius: 4px; border-top-right-radius: 4px; mso-cellspacing: 9px; border-spacing: 9px; max-width: 570px; -mru-width: 0px;\">" +
                "<tbody><tr>" +
                "<td align=\"center\" valign=\"top\" style=\"font-size: 0; border: 0;\"><div style=\"width: 100%; max-width: 552px; -mru-width: 0px;\"><!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"552\"><tr><![endif]--><!--" +
                "--><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]><td align=\"left\" valign=\"top\" width=\"184\"><![endif]--><!--" +
                "--><div class=\"mobile-full\" style=\"display: inline-block; vertical-align: top; width: 100%; max-width: 184px; -mru-width: 0px; min-width: calc(33.333333333333336%); max-width: calc(100%); width: calc(304704px - 55200%);\"><!--" +
                "--><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" width=\"184\" align=\"left\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px; -yandex-p: calc(2px - 3%);\">" +
                "<tbody><tr><td width=\"100%\" valign=\"top\" align=\"center\" class=\"links-color\"><!--[if (lte ie 8)]><div style=\"display: inline-block; width: 186px; -mru-width: 0px;\"><![endif]--><img border=\"0\" hspace=\"0\" align=\"left\" vspace=\"0\" width=\"186\" style=\"border: 0px; display: block; vertical-align: top; height: auto; color: #9bbb59; font-size: 13px; font-family: Arial, Helvetica, sans-serif; width: 100%; max-width: 186px; height: auto;\" src=\"https://ao-aws-dev-web-hosted-templates.s3.af-south-1.amazonaws.com/adumoonline_RGB.png\"><!--[if (lte ie 8)]></div><![endif]--></td></tr>" +
                "</tbody></table><!--" +
                "--></div><!--[if (gte mso 9)|(lte ie 8)]></td><![endif]--><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]><td align=\"left\" valign=\"top\" width=\"368\"><![endif]--><!--" +
                "--><div class=\"mobile-full\" style=\"display: inline-block; vertical-align: top; width: 100%; max-width: 368px; -mru-width: 0px; min-width: calc(66.66666666666667%); max-width: calc(100%); width: calc(304704px - 55200%);\"><!--" +
                "--><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" width=\"368\" align=\"left\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px; -yandex-p: calc(2px - 3%);\">" +
                "<tbody><tr>" +
                "<td width=\"100%\" valign=\"top\" align=\"left\" style=\"font-weight: normal; color: black; font-size: 18px; font-family: Arial, Helvetica, sans-serif; text-align: left;\"><span style=\"font-weight: normal;\">" + title + "</span></td>" +
                "</tr>" +
                "<tr><td class=\"long-text links-color\" width=\"100%\" valign=\"top\" align=\"left\" style=\"font-weight: normal; color: black; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: left; line-height: normal;\"><p style=\"margin: 1em 0px; margin-bottom: 0px; margin-top: 0px;\">" + description + "</p></td></tr>" +
                "<tr>" +
                "<td valign=\"top\" align=\"left\"><table role=\"presentation\" cellpadding=\"6\" border=\"0\" align=\"left\" cellspacing=\"0\" style=\"border-spacing: 0; mso-padding-alt: 6px 6px 6px 6px; padding-top: 4px;\"><tbody><tr>" +
                "<td width=\"auto\" valign=\"middle\" align=\"left\" bgcolor=\"#bfbfbf\" style=\"text-align: center; font-weight: normal; padding-top: 10px; padding-bottom: 10px; padding-left: 0; padding-right: 0; color: #000000; font-size: 13px; font-family: Arial, Helvetica, sans-serif; border: 1px solid #b9b9b9; background-color: white; border-radius: 4px;\"><p style=\"text-decoration: none; margin: 0; font-weight: normal; color: black; font-size: 13px; padding: 0px 16px; font-family: Arial, Helvetica, sans-serif; cursor: pointer;\">" + otp + "</p></td>" +
                "</tr></tbody></table></td>" +
                "</tr>" +
                "<tr><td class=\"long-text links-color\" width=\"100%\" valign=\"top\" align=\"left\" style=\"font-weight: normal; color: black; font-size: 11px; font-family: Arial, Helvetica, sans-serif; text-align: left; line-height: normal;\"><p style=\"margin: 1em 0px; margin-bottom: 0px; margin-top: 0px;\">" + "if you did not request this otp, you may safely ignore this email" + "</p></td></tr>" +
                "</tbody></table><!--" +
                "--></div><!--[if (gte mso 9)|(lte ie 8)]></td><![endif]--><!--" +
                "--><!--" +
                "--><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]></tr></table><![endif]--></div></td>" +
                "</tr>" +
                "</tbody></table></div><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->" +
                "</td></tr>" +
                "</tbody></table>" +
                "<!-- footerBlock -->" +
                "<table role=\"presentation\" class=\"vb-outer\" width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"background-color: #ffffff; border-left: 1px solid #b9b9b9; border-right: 1px solid #b9b9b9; border-bottom: 1px solid #b9b9b9; border-bottom-left-radius: 4px; border-bottom-right-radius: 4px; max-width: 570px;\" id=\"ko_footerBlock_2\">" +
                "<tbody><tr><td class=\"vb-outer\" align=\"center\" valign=\"top\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\">" +
                "<!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--" +
                "--><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 0px; border-spacing: 0px; max-width: 570px; -mru-width: 0px;\" width=\"570\" class=\"vb-row\">" +
                "<tbody><tr>" +
                "<td align=\"center\" valign=\"top\" style=\"font-size: 0; padding: 0 9px;\"><div style=\"vertical-align: top; width: 100%; max-width: 552px; -mru-width: 0px;\"><!--" +
                "--><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px;\" width=\"552\">" +
                "<tbody><tr><td class=\"long-text links-color\" width=\"100%\" valign=\"top\" align=\"center\" style=\"font-weight: normal; color: #919191; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: center;\"><p style=\"margin: 1em 0px; margin-bottom: 0px; margin-top: 0px;\">Email sent from <a href=\"https://adumoonline.com\" style=\"color: #cccccc; color: #cccccc; text-decoration: underline; cursor: pointer;\">adumoonline.com</a></p></td></tr>" +
                "</tbody></table></div></td>" +
                "</tr>" +
                "</tbody></table></div><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->" +
                "</td></tr>" +
                "</tbody></table>" +
                "<!-- /footerBlock -->" +
                "</center><!--[if !(gte mso 16)]--></body><!--<![endif]--></html>";
    }

    public String setEmailTemplate(String name, String url) {
        String description = "Hi " + name + "<br><br>" +
                "You are receiving this email because a back-office account has been registered for you. <br><br>" +
                "Please click on the link below to finish creating your account:";
        String title = "Adumo Online Back Office Account Registration";
        return "<!DOCTYPE html>" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "<meta name=\"viewport\" content=\"initial-scale=1.0\">" +
                "<meta name=\"format-detection\" content=\"telephone=no\">" +
                "<title>" + title + "</title>" +
                "<style type=\"text/css\">" +
                "body{ margin: 0; padding: 0; }" +
                "img{ border: 0px; display: block; }" +
                ".socialLinks{ font-size: 6px; }" +
                ".socialLinks a{" +
                "display: inline-block;" +
                "}" +
                ".long-text p{ margin: 1em 0px; }" +
                ".long-text p:last-child{ margin-bottom: 0px; }" +
                ".long-text p:first-child{ margin-top: 0px; }" +
                "</style>" +
                "<style type=\"text/css\">" +
                "/* yahoo, hotmail */" +
                ".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{ line-height: 100%; }" +
                ".yshortcuts a{ border-bottom: none !important; }" +
                ".vb-outer{ min-width: 0 !important; }" +
                ".RMsgBdy, .ExternalClass{" +
                "width: 100%;" +
                "background-color: #ffffff;" +
                "background-color: #ffffff}" +
                "/* outlook/office365 add buttons outside not-linked images and safari have 2px margin */" +
                "[o365] button{ margin: 0 !important; }" +
                "/* outlook */" +
                "table{ mso-table-rspace: 0pt; mso-table-lspace: 0pt; }" +
                "#outlook a{ padding: 0; }" +
                "img{ outline: none; text-decoration: none; border: none; -ms-interpolation-mode: bicubic; }" +
                "a img{ border: none; }" +
                "@media screen and (max-width: 600px) {" +
                "table.vb-container, table.vb-row{" +
                "width: 95% !important;" +
                "}" +
                ".mobile-hide{ display: none !important; }" +
                ".mobile-textcenter{ text-align: center !important; }" +
                ".mobile-full{ " +
                "width: 100% !important;" +
                "max-width: none !important;" +
                "}" +
                "}" +
                "/* previously used also screen and (max-device-width: 600px) but Yahoo Mail doesn't support multiple queries */" +
                "</style>" +
                "<style type=\"text/css\">" +
                "" +
                "#ko_sideArticleBlock_8 .links-color a, #ko_sideArticleBlock_8 .links-color a:link, #ko_sideArticleBlock_8 .links-color a:visited, #ko_sideArticleBlock_8 .links-color a:hover{" +
                "color: #ffffff;" +
                "color: #000000;" +
                "text-decoration: underline" +
                "}" +
                "#ko_footerBlock_2 .links-color a, #ko_footerBlock_2 .links-color a:link, #ko_footerBlock_2 .links-color a:visited, #ko_footerBlock_2 .links-color a:hover{" +
                "color: #cccccc;" +
                "color: #cccccc;" +
                "text-decoration: underline" +
                "}" +
                "</style>" +
                "</head>" +
                "<!--[if !(gte mso 16)]-->" +
                "<body bgcolor=\"#ffffff\" text=\"#919191\" alink=\"#cccccc\" vlink=\"#cccccc\" style=\"margin: 0; padding: 0; background-color: #ffffff; color: #919191;\"><!--<![endif]--><center>" +
                "<table role=\"presentation\" class=\"vb-outer\" width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"background-color: #ffffff;\" id=\"ko_sideArticleBlock_8\">" +
                "<tbody><tr><td class=\"vb-outer\" align=\"center\" valign=\"top\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\">" +
                "<!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--" +
                "--><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" cellpadding=\"0\" border=\"1px solid #b9b9b9;\" border-collapse=\"separate\" border-radius=\"4px\" margin-top=\"12px\" margin-bottom=\"0px\" bgcolor=\"#ffffff\" cellspacing=\"9\" width=\"570\" class=\"vb-row\" style=\"border: 1px solid #b9b9b9; margin-top: 12px; margin-bottom: 0px; border-collapse: separate; width: 100%; background-color: #ffffff; border-top-left-radius: 4px; border-top-right-radius: 4px; mso-cellspacing: 9px; border-spacing: 9px; max-width: 570px; -mru-width: 0px;\">" +
                "<tbody><tr>" +
                "<td align=\"center\" valign=\"top\" style=\"font-size: 0; border: 0;\"><div style=\"width: 100%; max-width: 552px; -mru-width: 0px;\"><!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"552\"><tr><![endif]--><!--" +
                "--><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]><td align=\"left\" valign=\"top\" width=\"184\"><![endif]--><!--" +
                "--><div class=\"mobile-full\" style=\"display: inline-block; vertical-align: top; width: 100%; max-width: 184px; -mru-width: 0px; min-width: calc(33.333333333333336%); max-width: calc(100%); width: calc(304704px - 55200%);\"><!--" +
                "--><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" width=\"184\" align=\"left\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px; -yandex-p: calc(2px - 3%);\">" +
                "<tbody><tr><td width=\"100%\" valign=\"top\" align=\"center\" class=\"links-color\"><!--[if (lte ie 8)]><div style=\"display: inline-block; width: 186px; -mru-width: 0px;\"><![endif]--><img border=\"0\" hspace=\"0\" align=\"left\" vspace=\"0\" width=\"186\" style=\"border: 0px; display: block; vertical-align: top; height: auto; color: #9bbb59; font-size: 13px; font-family: Arial, Helvetica, sans-serif; width: 100%; max-width: 186px; height: auto;\" src=\"https://ao-aws-dev-web-hosted-templates.s3.af-south-1.amazonaws.com/adumoonline_RGB.png\"><!--[if (lte ie 8)]></div><![endif]--></td></tr>" +
                "</tbody></table><!--" +
                "--></div><!--[if (gte mso 9)|(lte ie 8)]></td><![endif]--><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]><td align=\"left\" valign=\"top\" width=\"368\"><![endif]--><!--" +
                "--><div class=\"mobile-full\" style=\"display: inline-block; vertical-align: top; width: 100%; max-width: 368px; -mru-width: 0px; min-width: calc(66.66666666666667%); max-width: calc(100%); width: calc(304704px - 55200%);\"><!--" +
                "--><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" width=\"368\" align=\"left\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px; -yandex-p: calc(2px - 3%);\">" +
                "<tbody><tr>" +
                "<td width=\"100%\" valign=\"top\" align=\"left\" style=\"font-weight: normal; color: black; font-size: 18px; font-family: Arial, Helvetica, sans-serif; text-align: left;\"><span style=\"font-weight: normal;\">" + title + "</span></td>" +
                "</tr>" +
                "<tr><td class=\"long-text links-color\" width=\"100%\" valign=\"top\" align=\"left\" style=\"font-weight: normal; color: black; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: left; line-height: normal;\"><p style=\"margin: 1em 0px; margin-bottom: 0px; margin-top: 0px;\">" + description + "</p></td></tr>" +
                "<tr>" +
                "<td valign=\"top\" align=\"left\"><table role=\"presentation\" cellpadding=\"6\" border=\"0\" align=\"left\" cellspacing=\"0\" style=\"border-spacing: 0; mso-padding-alt: 6px 6px 6px 6px; padding-top: 4px;\"><tbody><tr>" +
                "<td width=\"auto\" valign=\"middle\" align=\"left\" bgcolor=\"#bfbfbf\" style=\"text-align: center; font-weight: normal; padding-top: 10px; padding-bottom: 10px; padding-left: 0; padding-right: 0; background-color: #FF666E; color: #000000; font-size: 13px; font-family: Arial, Helvetica, sans-serif; border-radius: 4px;\"><a style=\"text-decoration: none; font-weight: normal; color: black; font-size: 13px; padding: 10px; font-family: Arial, Helvetica, sans-serif; cursor: pointer;\" target=\"_new\" href=\"" + url + "\">Register Account</a></td>" +
                "</tr></tbody></table></td>" +
                "</tr>" +
                "</tbody></table><!--" +
                "--></div><!--[if (gte mso 9)|(lte ie 8)]></td><![endif]--><!--" +
                "--><!--" +
                "--><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]></tr></table><![endif]--></div></td>" +
                "</tr>" +
                "</tbody></table></div><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->" +
                "</td></tr>" +
                "</tbody></table>" +
                "<!-- footerBlock -->" +
                "<table role=\"presentation\" class=\"vb-outer\" width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"background-color: #ffffff; border-left: 1px solid #b9b9b9; border-right: 1px solid #b9b9b9; border-bottom: 1px solid #b9b9b9; border-bottom-left-radius: 4px; border-bottom-right-radius: 4px; max-width: 570px;\" id=\"ko_footerBlock_2\">" +
                "<tbody><tr><td class=\"vb-outer\" align=\"center\" valign=\"top\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\">" +
                "<!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--" +
                "--><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 0px; border-spacing: 0px; max-width: 570px; -mru-width: 0px;\" width=\"570\" class=\"vb-row\">" +
                "<tbody><tr>" +
                "<td align=\"center\" valign=\"top\" style=\"font-size: 0; padding: 0 9px;\"><div style=\"vertical-align: top; width: 100%; max-width: 552px; -mru-width: 0px;\"><!--" +
                "--><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px;\" width=\"552\">" +
                "<tbody><tr><td class=\"long-text links-color\" width=\"100%\" valign=\"top\" align=\"center\" style=\"font-weight: normal; color: #919191; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: center;\"><p style=\"margin: 1em 0px; margin-bottom: 0px; margin-top: 0px;\">Email sent from <a href=\"https://adumoonline.com\" style=\"color: #cccccc; color: #cccccc; text-decoration: underline; cursor: pointer;\">adumoonline.com</a></p></td></tr>" +
                "</tbody></table></div></td>" +
                "</tr>" +
                "</tbody></table></div><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->" +
                "</td></tr>" +
                "</tbody></table>" +
                "<!-- /footerBlock -->" +
                "</center><!--[if !(gte mso 16)]--></body><!--<![endif]--></html>";
//        return  "<html>" +
//                "<body style=\"font-family: Arial, Helvetica, sans-serif; color: white; background-color: #021C30;\">" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr><p style=\"font-size: 16px; white-space: pre;\"> </p></tr>" +
//                "<tr>" +
//                "<th>" +
//                "<img src=\"https://wirecard-static.s3.af-south-1.amazonaws.com/activation-email-icon.png\" width=\"100px\">" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr>" +
//                "<th>" +
//                "<h1 style=\"color: white !important;\">User Activation</h1>" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr>" +
//                "<th></th>" +
//                "<th width=\"25%\" style=\"background-color: #ff7373; border-radius: 24px;\">" +
//                "<div style=\"font-size: 12px; white-space: pre;\"> </div>" +
//                "<div><a style=\"color: white; font-size: 14px; font-weight: 600; border: none;\" href=\"" + url + "\">VERIFY EMAIL & SET PASSWORD</a></div>" +
//                "<div style=\"font-size: 12px; white-space: pre;\"> </div>" +
//                "</th>" +
//                "<th></th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr><p style=\"font-size: 8px; white-space: pre;\"> </p></tr>" +
//                "<tr>" +
//                "<th>" +
//                "<p style=\"font-size: 12px; margin: 0; color: white !important;\">Please click on the link above to verify</p>" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr>" +
//                "<th>" +
//                "<p style=\"font-size: 12px; margin: 0; color: white !important;\">your email address and set your</p>" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr>" +
//                "<th>" +
//                "<p style=\"font-size: 12px; margin: 0; color: white !important;\">password</p>" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table>" +
//                "<tr><p style=\"font-size: 10px; white-space: pre;\"> </p></tr>" +
//                "</table>" +
//                "</div>" +
//                "</body>" +
//                "</html>";
    }

    public String setResetPasswordEmailTemplate(String url, String name) {
//        return  "<html>" +
//                "<body style=\"font-family: Arial, Helvetica, sans-serif; color: white; background-color: #021C30;\">" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr><p style=\"font-size: 16px; white-space: pre;\"> </p></tr>" +
//                "<tr>" +
//                "<th>" +
//                "<img src=\"https://wirecard-static.s3.af-south-1.amazonaws.com/activation-email-icon.png\" width=\"100px\">" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr>" +
//                "<th>" +
//                "<h1 style=\"color: white !important;\">Reset Password</h1>" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr>" +
//                "<th></th>" +
//                "<th width=\"20%\" style=\"background-color: #ff7373; border-radius: 24px;\">" +
//                "<div style=\"font-size: 12px; white-space: pre;\"> </div>" +
//                "<div>" +
//                "<a style=\"color: white; font-size: 14px; font-weight: 600; border: none;\" href=\"" + url + "\">CHANGE PASSWORD</a>" +
//                "</div>" +
//                "<div style=\"font-size: 12px; white-space: pre;\"> </div>" +
//                "</th>" +
//                "<th></th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr><p style=\"font-size: 8px; white-space: pre;\"> </p></tr>" +
//                "<tr>" +
//                "<th>" +
//                "<p style=\"font-size: 12px; margin: 0; color: white !important;\">Please click on the link above to change</p>" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table width=\"100%\" height=\"100%\" style=\"border-collapse: collapse;\">" +
//                "<tr>" +
//                "<th>" +
//                "<p style=\"font-size: 12px; margin: 0; color: white !important;\">your password</p>" +
//                "</th>" +
//                "</tr>" +
//                "</table>" +
//                "</div>" +
//                "<div>" +
//                "<table>" +
//                "<tr><p style=\"font-size: 10px; white-space: pre;\"> </p></tr>" +
//                "</table>" +
//                "</div>" +
//                "</body>" +
//                "</html>";
        // String title = ""
        String description = "Hi " + name + "<br><br>" +
            "You are receiving this email because you requested to reset your back-office password. <br><br>" +
            "Please click on the link below to have it reset:";
        String title = "Adumo Online Back Office Password Reset";
        return "<!DOCTYPE html>" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "<meta name=\"viewport\" content=\"initial-scale=1.0\">" +
                "<meta name=\"format-detection\" content=\"telephone=no\">" +
                "<title>" + title + "</title>" +
                "<style type=\"text/css\">" +
                "body{ margin: 0; padding: 0; }" +
                "img{ border: 0px; display: block; }" +
                ".socialLinks{ font-size: 6px; }" +
                ".socialLinks a{" +
                "display: inline-block;" +
                "}" +
                ".long-text p{ margin: 1em 0px; }" +
                ".long-text p:last-child{ margin-bottom: 0px; }" +
                ".long-text p:first-child{ margin-top: 0px; }" +
                "</style>" +
                "<style type=\"text/css\">" +
                "/* yahoo, hotmail */" +
                ".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{ line-height: 100%; }" +
                ".yshortcuts a{ border-bottom: none !important; }" +
                ".vb-outer{ min-width: 0 !important; }" +
                ".RMsgBdy, .ExternalClass{" +
                "width: 100%;" +
                "background-color: #ffffff;" +
                "background-color: #ffffff}" +
                "/* outlook/office365 add buttons outside not-linked images and safari have 2px margin */" +
                "[o365] button{ margin: 0 !important; }" +
                "/* outlook */" +
                "table{ mso-table-rspace: 0pt; mso-table-lspace: 0pt; }" +
                "#outlook a{ padding: 0; }" +
                "img{ outline: none; text-decoration: none; border: none; -ms-interpolation-mode: bicubic; }" +
                "a img{ border: none; }" +
                "@media screen and (max-width: 600px) {" +
                "table.vb-container, table.vb-row{" +
                "width: 95% !important;" +
                "}" +
                ".mobile-hide{ display: none !important; }" +
                ".mobile-textcenter{ text-align: center !important; }" +
                ".mobile-full{ " +
                "width: 100% !important;" +
                "max-width: none !important;" +
                "}" +
                "}" +
                "/* previously used also screen and (max-device-width: 600px) but Yahoo Mail doesn't support multiple queries */" +
                "</style>" +
                "<style type=\"text/css\">" +
                "" +
                "#ko_sideArticleBlock_8 .links-color a, #ko_sideArticleBlock_8 .links-color a:link, #ko_sideArticleBlock_8 .links-color a:visited, #ko_sideArticleBlock_8 .links-color a:hover{" +
                "color: #ffffff;" +
                "color: #000000;" +
                "text-decoration: underline" +
                "}" +
                "#ko_footerBlock_2 .links-color a, #ko_footerBlock_2 .links-color a:link, #ko_footerBlock_2 .links-color a:visited, #ko_footerBlock_2 .links-color a:hover{" +
                "color: #cccccc;" +
                "color: #cccccc;" +
                "text-decoration: underline" +
                "}" +
                "</style>" +
                "</head>" +
                "<!--[if !(gte mso 16)]-->" +
                "<body bgcolor=\"#ffffff\" text=\"#919191\" alink=\"#cccccc\" vlink=\"#cccccc\" style=\"margin: 0; padding: 0; background-color: #ffffff; color: #919191;\"><!--<![endif]--><center>" +
                "<table role=\"presentation\" class=\"vb-outer\" width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"background-color: #ffffff;\" id=\"ko_sideArticleBlock_8\">" +
                "<tbody><tr><td class=\"vb-outer\" align=\"center\" valign=\"top\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\">" +
                "<!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--" +
                "--><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" cellpadding=\"0\" border=\"1px solid #b9b9b9;\" border-collapse=\"separate\" border-radius=\"4px\" margin-top=\"12px\" margin-bottom=\"0px\" bgcolor=\"#ffffff\" cellspacing=\"9\" width=\"570\" class=\"vb-row\" style=\"border: 1px solid #b9b9b9; margin-top: 12px; margin-bottom: 0px; border-collapse: separate; width: 100%; background-color: #ffffff; border-top-left-radius: 4px; border-top-right-radius: 4px; mso-cellspacing: 9px; border-spacing: 9px; max-width: 570px; -mru-width: 0px;\">" +
                "<tbody><tr>" +
                "<td align=\"center\" valign=\"top\" style=\"font-size: 0; border: 0;\"><div style=\"width: 100%; max-width: 552px; -mru-width: 0px;\"><!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"552\"><tr><![endif]--><!--" +
                "--><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]><td align=\"left\" valign=\"top\" width=\"184\"><![endif]--><!--" +
                "--><div class=\"mobile-full\" style=\"display: inline-block; vertical-align: top; width: 100%; max-width: 184px; -mru-width: 0px; min-width: calc(33.333333333333336%); max-width: calc(100%); width: calc(304704px - 55200%);\"><!--" +
                "--><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" width=\"184\" align=\"left\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px; -yandex-p: calc(2px - 3%);\">" +
                "<tbody><tr><td width=\"100%\" valign=\"top\" align=\"center\" class=\"links-color\"><!--[if (lte ie 8)]><div style=\"display: inline-block; width: 186px; -mru-width: 0px;\"><![endif]--><img border=\"0\" hspace=\"0\" align=\"left\" vspace=\"0\" width=\"186\" style=\"border: 0px; display: block; vertical-align: top; height: auto; color: #9bbb59; font-size: 13px; font-family: Arial, Helvetica, sans-serif; width: 100%; max-width: 186px; height: auto;\" src=\"https://ao-aws-dev-web-hosted-templates.s3.af-south-1.amazonaws.com/adumoonline_RGB.png\"><!--[if (lte ie 8)]></div><![endif]--></td></tr>" +
                "</tbody></table><!--" +
                "--></div><!--[if (gte mso 9)|(lte ie 8)]></td><![endif]--><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]><td align=\"left\" valign=\"top\" width=\"368\"><![endif]--><!--" +
                "--><div class=\"mobile-full\" style=\"display: inline-block; vertical-align: top; width: 100%; max-width: 368px; -mru-width: 0px; min-width: calc(66.66666666666667%); max-width: calc(100%); width: calc(304704px - 55200%);\"><!--" +
                "--><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" width=\"368\" align=\"left\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px; -yandex-p: calc(2px - 3%);\">" +
                "<tbody><tr>" +
                "<td width=\"100%\" valign=\"top\" align=\"left\" style=\"font-weight: normal; color: black; font-size: 18px; font-family: Arial, Helvetica, sans-serif; text-align: left;\"><span style=\"font-weight: normal;\">" + title + "</span></td>" +
                "</tr>" +
                "<tr><td class=\"long-text links-color\" width=\"100%\" valign=\"top\" align=\"left\" style=\"font-weight: normal; color: black; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: left; line-height: normal;\"><p style=\"margin: 1em 0px; margin-bottom: 0px; margin-top: 0px;\">" + description + "</p></td></tr>" +
                "<tr>" +
                "<td valign=\"top\" align=\"left\"><table role=\"presentation\" cellpadding=\"6\" border=\"0\" align=\"left\" cellspacing=\"0\" style=\"border-spacing: 0; mso-padding-alt: 6px 6px 6px 6px; padding-top: 4px;\"><tbody><tr>" +
                "<td width=\"auto\" valign=\"middle\" align=\"left\" bgcolor=\"#bfbfbf\" style=\"text-align: center; font-weight: normal; padding-top: 10px; padding-bottom: 10px; padding-left: 0; padding-right: 0; background-color: #FF666E; color: #000000; font-size: 13px; font-family: Arial, Helvetica, sans-serif; border-radius: 4px;\"><a style=\"text-decoration: none; font-weight: normal; color: black; font-size: 13px; padding: 10px; font-family: Arial, Helvetica, sans-serif; cursor: pointer;\" target=\"_new\" href=\"" + url + "\">Reset Password</a></td>" +
                "</tr></tbody></table></td>" +
                "</tr>" +
                "<tr><td class=\"long-text links-color\" width=\"100%\" valign=\"top\" align=\"left\" style=\"font-weight: normal; color: black; font-size: 11px; font-family: Arial, Helvetica, sans-serif; text-align: left; line-height: normal;\"><p style=\"margin: 1em 0px; margin-bottom: 0px; margin-top: 0px;\">" + "if you did not request this password change, you may safely ignore this email" + "</p></td></tr>" +
                "</tbody></table><!--" +
                "--></div><!--[if (gte mso 9)|(lte ie 8)]></td><![endif]--><!--" +
                "--><!--" +
                "--><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]></tr></table><![endif]--></div></td>" +
                "</tr>" +
                "</tbody></table></div><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->" +
                "</td></tr>" +
                "</tbody></table>" +
                "<!-- footerBlock -->" +
                "<table role=\"presentation\" class=\"vb-outer\" width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"background-color: #ffffff; border-left: 1px solid #b9b9b9; border-right: 1px solid #b9b9b9; border-bottom: 1px solid #b9b9b9; border-bottom-left-radius: 4px; border-bottom-right-radius: 4px; max-width: 570px;\" id=\"ko_footerBlock_2\">" +
                "<tbody><tr><td class=\"vb-outer\" align=\"center\" valign=\"top\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\">" +
                "<!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--" +
                "--><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 0px; border-spacing: 0px; max-width: 570px; -mru-width: 0px;\" width=\"570\" class=\"vb-row\">" +
                "<tbody><tr>" +
                "<td align=\"center\" valign=\"top\" style=\"font-size: 0; padding: 0 9px;\"><div style=\"vertical-align: top; width: 100%; max-width: 552px; -mru-width: 0px;\"><!--" +
                "--><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px;\" width=\"552\">" +
                "<tbody><tr><td class=\"long-text links-color\" width=\"100%\" valign=\"top\" align=\"center\" style=\"font-weight: normal; color: #919191; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: center;\"><p style=\"margin: 1em 0px; margin-bottom: 0px; margin-top: 0px;\">Email sent from <a href=\"https://adumoonline.com\" style=\"color: #cccccc; color: #cccccc; text-decoration: underline; cursor: pointer;\">adumoonline.com</a></p></td></tr>" +
                "</tbody></table></div></td>" +
                "</tr>" +
                "</tbody></table></div><!--" +
                "--><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->" +
                "</td></tr>" +
                "</tbody></table>" +
                "<!-- /footerBlock -->" +
                "</center><!--[if !(gte mso 16)]--></body><!--<![endif]--></html>";
    }

    public boolean authUserPasswordValid(BackOfficeUserEntity backOfficeUserEntity) {
        BackOfficeUserPasswordHistoryEntity backOfficeUserPasswordHistoryEntity =
                backOfficeUserPasswordHistoryRepository.findDistinctFirstByBackOfficeUserByBackOfficeUserIdOrderByIdDesc(backOfficeUserEntity);
        if (backOfficeUserPasswordHistoryEntity == null) {
            // Create entry
            BackOfficeUserPasswordHistoryEntity backOfficeUserPasswordHistoryEntity1 = new BackOfficeUserPasswordHistoryEntity();
            backOfficeUserPasswordHistoryEntity1.setBackOfficeUserByBackOfficeUserId(backOfficeUserEntity);
            backOfficeUserPasswordHistoryEntity1.setPassword(backOfficeUserEntity.getPassword());
            backOfficeUserPasswordHistoryEntity1.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            backOfficeUserPasswordHistoryRepository.save(backOfficeUserPasswordHistoryEntity1);
            return true;
        }



        // Check if password is 90 days old or older.

        Calendar cPasswordValidityCutoffTime = Calendar.getInstance();
        cPasswordValidityCutoffTime.set(Calendar.HOUR_OF_DAY, -1440);
        cPasswordValidityCutoffTime.set(Calendar.MINUTE, 0);
        cPasswordValidityCutoffTime.set(Calendar.SECOND, 0);
        cPasswordValidityCutoffTime.set(Calendar.MILLISECOND, 0);

        logger.info("Password created date nanos | " + backOfficeUserPasswordHistoryEntity.getCreatedDate().getTime());
        logger.info("Password 90 day cutoff millis | " + cPasswordValidityCutoffTime.getTimeInMillis());

        if (backOfficeUserPasswordHistoryEntity.getCreatedDate().getTime() <= cPasswordValidityCutoffTime.getTimeInMillis()) {
            return false;
        }

        return true;
    }

    public BackOfficeUserLoginEntity auditLoginAttempt(String ipAddress, String username) {

        Optional<BackOfficeUserEntity> backOfficeUserEntity = backOfficeUserRepository.findByEmailIgnoreCase(username);
        if (backOfficeUserEntity.isPresent()) {
            BackOfficeUserLoginEntity backOfficeUserLoginEntity = backOfficeUserLoginRepository.findByIpAddressAndBackOfficeUserByBackOfficeUserId(ipAddress, backOfficeUserEntity.get());
            if (backOfficeUserLoginEntity == null) {
                backOfficeUserLoginEntity = new BackOfficeUserLoginEntity();
            } else {
                if (backOfficeUserLoginEntity.getLoginAttemptCount() == 5) {
                    backOfficeUserEntity.get().setBlocked(true);
                    backOfficeUserRepository.save(backOfficeUserEntity.get());
                    throw new GenericException("Your account is locked, please wait a while before trying again", HttpStatus.CONFLICT, "Your account is locked, please wait a while before trying again");
                }
            }
            backOfficeUserLoginEntity.setBackOfficeUserByBackOfficeUserId(backOfficeUserEntity.get());
            backOfficeUserLoginEntity.setLoginAttemptCount((short) (backOfficeUserLoginEntity.getLoginAttemptCount() + 1));
            backOfficeUserLoginEntity.setIpAddress(ipAddress);
            backOfficeUserLoginEntity.setLatestAttemptDate(new Timestamp(System.currentTimeMillis()));
            backOfficeUserLoginRepository.save(backOfficeUserLoginEntity);
            return backOfficeUserLoginEntity;
        }
        return null;
    }

    public void checkLockout(String ipAddress, String username) {
        Optional<BackOfficeUserEntity> backOfficeUserEntity = backOfficeUserRepository.findByEmailIgnoreCase(username);
        if (backOfficeUserEntity.isPresent()) {
            logger.info(String.format("Is | %s | blocked | %s", backOfficeUserEntity.get().getEmail(), backOfficeUserEntity.get().getBlocked()));
            BackOfficeUserLoginEntity backOfficeUserLoginEntity = backOfficeUserLoginRepository.findByIpAddressAndBackOfficeUserByBackOfficeUserId(ipAddress, backOfficeUserEntity.get());
            if (backOfficeUserLoginEntity == null) {
                backOfficeUserLoginEntity = new BackOfficeUserLoginEntity();
            } else {
                if (backOfficeUserLoginEntity.getLoginAttemptCount() == 5 && backOfficeUserEntity.get().getBlocked()) {
                    throw new GenericException("Your account is locked, please wait a while before trying again", HttpStatus.CONFLICT, "Your account is locked, please wait a while before trying again");
                }
            }
        }
    }

    private String getIpAddressFromServletRequest(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
            if (remoteAddr.contains(",")) {
                remoteAddr = remoteAddr.replace(",", "|");
            }
        }
        if (remoteAddr.contains(",")) {
            remoteAddr.replace(",", "|");
        }
        return remoteAddr;
    }

    @Transactional
    public void deleteLoginAttempts(String ipAddress, BackOfficeUserEntity backOfficeUserEntity, Token token) {
        backOfficeUserLoginRepository.deleteAllByIpAddressAndBackOfficeUserByBackOfficeUserId(ipAddress, backOfficeUserEntity);
    }

    public void auditLogin(BackOfficeUserEntity backOfficeUserEntity) {
        backOfficeUserEntity.setLastLoginDate(new Timestamp(System.currentTimeMillis()));
        backOfficeUserRepository.save(backOfficeUserEntity);
    }

    // method for activating user


}
