package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import za.co.wirecard.channel.backoffice.config.UtilityService;
import za.co.wirecard.channel.backoffice.dto.models.responses.XeroAuthorizationResponse;
import za.co.wirecard.channel.backoffice.exceptions.XeroAuthorizationException;

@Service
public class XeroServiceImpl implements XeroService{

    @Value("${api.xero.authorization}")
    private String xeroAuthorizationUrl;

    private static final Logger logger = LogManager.getLogger(XeroServiceImpl.class);

    private final RestTemplate restTemplate;
    private final UtilityService utilityService;

    public XeroServiceImpl(RestTemplate restTemplate,
                           UtilityService utilityService) {
        this.restTemplate = restTemplate;
        this.utilityService = utilityService;
    }

    @Override
    public XeroAuthorizationResponse xeroAuthorization() throws IOException {
        logger.info("Received request for Xero Authorization");
        XeroAuthorizationResponse response;
        try {
            response =
                restTemplate.exchange(
                    xeroAuthorizationUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(utilityService.getHttpHeaders()),
                    XeroAuthorizationResponse.class)
                .getBody();
        } catch (RestClientResponseException e) {
            throw new XeroAuthorizationException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("Successfully processed request for Xero Authorization | " + response.getUrl());
        return response;
    }
}
