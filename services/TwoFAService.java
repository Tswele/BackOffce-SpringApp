package za.co.wirecard.channel.backoffice.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.config.VaultConfig;
import za.co.wirecard.channel.backoffice.dto.models.responses.Create2FAResponse;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.email.EmailNotification;
import za.co.wirecard.channel.backoffice.mq.SendEmail;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;

@Service
public class TwoFAService {

    private final BackOfficeUserRepository backOfficeUserRepository;
    private final GoogleAuthenticatorService googleAuthenticatorService;
    private final JavaMailSender javaMailSender;
    private final SendEmail sendEmail;
    private final AuthenticationService authenticationService;
    private final PlatformServices platformServices;
    private final VaultConfig vaultConfig;
    private static final Logger logger = LogManager.getLogger(TwoFAService.class);

    public TwoFAService(BackOfficeUserRepository backOfficeUserRepository, GoogleAuthenticatorService googleAuthenticatorService, JavaMailSender javaMailSender, SendEmail sendEmail, AuthenticationService authenticationService, PlatformServices platformServices, VaultConfig vaultConfig) {
        this.backOfficeUserRepository = backOfficeUserRepository;
        this.googleAuthenticatorService = googleAuthenticatorService;
        this.javaMailSender = javaMailSender;
        this.sendEmail = sendEmail;
        this.authenticationService = authenticationService;
        this.platformServices = platformServices;
        this.vaultConfig = vaultConfig;
    }

    public Create2FAResponse createTwoFactorAuth(long id, String username) {
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne(id);
        // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        String secretKey = googleAuthenticatorService.generateSecretKey();
        String qrCodeStr = googleAuthenticatorService.getGoogleAuthenticatorBarCode(username, secretKey);
        // backOfficeUserEntity.setSecretKey(passwordEncoder.encode(secretKey));
        // logger.info("NEW ENCRYPTED SECRET_KEY | " + platformServices.encrypt(vaultConfig.getOtpSecretKey(), secretKey));
        //backOfficeUserEntity.setSecretKey(platformServices.encrypt(vaultConfig.getOtpSecretKey(), secretKey));
        backOfficeUserEntity.setSecretKey(secretKey);
        // GoogleAuthenticatorService.createQRCode(qrCodeStr, "src/main/resources/output.png", 300, 300);
        // Get AWS S3 Bucket qr code url
        // googleAuthenticatorService.createQRCode(qrCodeStr, "src/main/resources/output", 300, 300);
        // GetObjectUrl getObjectUrl = s3BucketService.uploadFileToS3Bucket(constructMultipartImageFromQrCodeUrl());
        Create2FAResponse getObjectUrl = new Create2FAResponse(qrCodeStr);
        backOfficeUserRepository.save(backOfficeUserEntity);
        return ResponseEntity.ok(getObjectUrl).getBody();
    }

    public void enableTwoFA(long userId) {
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne(userId);
        if (backOfficeUserEntity == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND, "Could not find user with id | " + userId);
        }
        backOfficeUserEntity.setTwoFaEnabled(true);
        backOfficeUserRepository.save(backOfficeUserEntity);
    }

    public ResponseEntity<?> confirmOtp(long userId, String otp) {
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne(userId);
        if (backOfficeUserEntity == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND, "Could not find user with id | " + userId);
        }
        if (StringUtils.isBlank(backOfficeUserEntity.getOtp())) {
            throw new GenericException("User has no otp", HttpStatus.NOT_FOUND, "No Otp could be found for user with id | " + userId);
        }
        if (backOfficeUserEntity.getOtp().equalsIgnoreCase(otp)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public void sendEmail(EmailNotification emailNotification) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("backofficeqa@gmail.com");
        helper.setTo(emailNotification.getToAdress());
        helper.setSubject(emailNotification.getSubject());
        helper.setText(emailNotification.getBody(), true);
        javaMailSender.send(message);
    }

    public ResponseEntity<?> resendOtp(long userId) {
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne(userId);
        if (backOfficeUserEntity == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND, "Could not find user with id | " + userId);
        }
        if (StringUtils.isBlank(backOfficeUserEntity.getOtp())) {
            throw new GenericException("User has no otp", HttpStatus.NOT_FOUND, "No Otp could be found for user with id | " + userId);
        }
        try {
            sendOtp(backOfficeUserEntity);
        } catch (Exception e) {

        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public void sendOtp(BackOfficeUserEntity backOfficeUserEntity) {
//        ResourceLoader resourceLoader = new DefaultResourceLoader();
//        Resource resource = resourceLoader.getResource("classpath:otpEmailTemplate.html");
//        Reader reader = null;
//        try {
//            reader = new InputStreamReader(resource.getInputStream(), UTF_8);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String htmlFile = null;
//        try {
//            htmlFile = FileCopyUtils.copyToString(reader);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Document doc = Jsoup.parse(htmlFile);
//        Element otpElement = doc.getElementById(Utils.OTP_HTML_ID);
//        Element otpParentElement = doc.getElementById(Utils.OTP_PARENT_HTML_ID);
        String otp = RandomStringUtils.random(5, "0123456789");;
//        otpElement.appendText(otpStr);
//        otpParentElement.append(otpElement.outerHtml());
        EmailNotification emailNotification = new EmailNotification(backOfficeUserEntity.getEmail(), "Back Office Otp", authenticationService.setEmailOtpTemplate(backOfficeUserEntity.getFirstName(), otp));
        sendEmail.send(emailNotification);
//        try {
//            sendEmail(emailNotification);
//        } catch (MessagingException e) {
//            throw new GenericException("Something went wrong with sending the email", HttpStatus.INTERNAL_SERVER_ERROR, "Email sending failed");
//        }
        backOfficeUserEntity.setOtp(otp);
        backOfficeUserRepository.save(backOfficeUserEntity);
    }
}
