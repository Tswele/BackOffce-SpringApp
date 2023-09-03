package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import za.co.wirecard.channel.backoffice.config.RestTemplateConfig;
import za.co.wirecard.channel.backoffice.config.UtilityService;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateApplicationPaymentLinkSettingRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetApplicationPaymentLinkSettingResponse;
import za.co.wirecard.channel.backoffice.exceptions.ApplicationPaymentLinkSettingNotCreatedException;
import za.co.wirecard.channel.backoffice.exceptions.ApplicationPaymentLinkSettingNotFoundException;
import za.co.wirecard.common.exceptions.RestTemplateResponseOrchestrationErrorHandler;

import javax.servlet.http.HttpServletRequest;

@Service
public class PaymentLinkServiceImpl implements PaymentLinkService {

    @Value("${api.paymentlink.setting.create}")
    private String paymentLinkCreateUrl;

    @Value("${api.paymentlink.setting.view}")
    private String paymentLinkViewUrl;

    private static final Logger logger = LogManager.getLogger(PaymentLinkServiceImpl.class);
    private final UtilityService utilityService;

    public PaymentLinkServiceImpl(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @Override
    public void createApplicationPaymentLinkSetting(CreateApplicationPaymentLinkSettingRequest createApplicationPaymentLinkSettingRequest, HttpServletRequest servletRequest) {
        RestTemplate restTemplate = new RestTemplateConfig().keycloakRestTemplate(servletRequest);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new RestTemplateResponseOrchestrationErrorHandler());

        logger.info(String.format("Attempting to create application payment link setting for application | %s", createApplicationPaymentLinkSettingRequest.getApplicationUid()));
        try {
            restTemplate.exchange ( paymentLinkCreateUrl,
                                    HttpMethod.POST,
                                    new HttpEntity<>(createApplicationPaymentLinkSettingRequest, utilityService.getHttpHeaders()),
                                    HttpStatus.class);
        } catch (RestClientResponseException e) {
            throw new ApplicationPaymentLinkSettingNotCreatedException( "Unable to create application payment link setting",
                                                                        HttpStatus.valueOf(e.getRawStatusCode()),
                                                                        utilityService.getReasonFromApiError(e.getResponseBodyAsString()));
        }
        logger.info("Successfully created application payment link setting");
    }

    @Override
    public ResponseEntity<GetApplicationPaymentLinkSettingResponse> viewApplicationPaymentLinkSetting(String merchantUid, String applicationUid, HttpServletRequest servletRequest) {
        RestTemplate restTemplate = new RestTemplateConfig().keycloakRestTemplate(servletRequest);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new RestTemplateResponseOrchestrationErrorHandler());

        logger.info(String.format("Attempting to view application payment link setting for application | %s", applicationUid));
        ResponseEntity<GetApplicationPaymentLinkSettingResponse> response;
        try {
            response = restTemplate.exchange (  String.format(paymentLinkViewUrl, merchantUid, applicationUid),
                                                HttpMethod.GET,
                                                new HttpEntity<>(null, utilityService.getHttpHeaders()),
                                                GetApplicationPaymentLinkSettingResponse.class);
        } catch (RestClientResponseException e) {
            throw new ApplicationPaymentLinkSettingNotFoundException ( "Unable to view application payment link setting",
                                                                        HttpStatus.valueOf(e.getRawStatusCode()),
                                                                        utilityService.getReasonFromApiError(e.getResponseBodyAsString()));
        }
        logger.info("Successfully viewed application payment link setting");
        response.getStatusCode().toString();
        return response;
    }

}

