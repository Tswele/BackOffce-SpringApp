package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
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
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateInterfaceRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetIntefaceByInterfaceIdResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetInterfacesByMerchantIdResponse;
import za.co.wirecard.channel.backoffice.exceptions.ExceptionExtender;
import za.co.wirecard.channel.backoffice.models.*;
import za.co.wirecard.common.exceptions.RestTemplateResponseOrchestrationErrorHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class InterfaceServiceImpl implements InterfaceService {
    @Value("${api.configurationmanagement.url}")
    private String configurationManagementUrl;

    private static final Logger logger = LogManager.getLogger(ApplicationService.class);
    private final UtilityService utilityService;
    private final RestTemplate restTemplate;

    public InterfaceServiceImpl(UtilityService utilityService, RestTemplateBuilder restTemplateBuilder) {
        this.utilityService = utilityService;
        this.restTemplate = restTemplateBuilder.build();
    }

    public RestTemplate restTemplateSetup(HttpServletRequest servletRequest) {
        RestTemplate restTemplate = new RestTemplateConfig().keycloakRestTemplate(servletRequest);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new RestTemplateResponseOrchestrationErrorHandler());
        return restTemplate;
    }

    @Override
    public List<Interface> getInterfaces(HttpServletRequest servletRequest) {
        RestTemplate restTemplate = restTemplateSetup(servletRequest);
        //String url = interfaceManagementUrl;
        String url = configurationManagementUrl + "/interfaces";
        ParameterizedTypeReference<List<Interface>> responseType = new ParameterizedTypeReference<List<Interface>>() {
        };
        ResponseEntity<List<Interface>> interfaces = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        logger.info("This is the Interfaces Object that gets returned: " + interfaces.getBody());
        return interfaces.getBody();
    }

    @Override
    public GetIntefaceByInterfaceIdResponse getInterfaceByInterfaceId(long interfaceId) throws ExceptionExtender {
        String url = configurationManagementUrl + "/interfaces" + "/" + interfaceId;
        ResponseEntity<GetIntefaceByInterfaceIdResponse> interfaceResponseEntity;
        try {
            interfaceResponseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), GetIntefaceByInterfaceIdResponse.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getMessage()), e.getResponseBodyAsString());
        }
        return interfaceResponseEntity.getBody();
    }

    @Override
    public void createInterface(PlatformCreateInterfaceRequest interfaceRequest, HttpServletRequest servletRequest) {
        RestTemplate restTemplate = restTemplateSetup(servletRequest);
        String url = configurationManagementUrl + "/interfaces";
        logger.info("Add Interface " + " URL " + " INTERFACE " + interfaceRequest);
        restTemplate.postForEntity(url, interfaceRequest, String.class);
    }

    @Override
    public void editInterfaceByInterfaceId(long interfaceId, PlatformCreateInterfaceRequest interfaceRequest) throws ExceptionExtender {
        String url = configurationManagementUrl + "/interfaces" + "/" + interfaceId;
        try {
           restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(interfaceRequest, utilityService.getHttpHeaders()), PlatformCreateInterfaceRequest.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getMessage() != null ? e.getMessage() : "MESSAGE_NULL"), e.getResponseBodyAsString());
        }
    }

    @Override
    public List<SecurityMethod> getSecurityMethods(HttpServletRequest servletRequest) {
        RestTemplate restTemplate = restTemplateSetup(servletRequest);
        String url = configurationManagementUrl + "/security-methods";
        ParameterizedTypeReference<List<SecurityMethod>> responseType = new ParameterizedTypeReference<List<SecurityMethod>>() {
        };
        ResponseEntity<List<SecurityMethod>> securityMethods = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return securityMethods.getBody();
    }

    @Override
    public List<TradingCurrency> getTradingCurrencies(HttpServletRequest servletRequest) {
        return null;
    }

    @Override
    public List<Currency> getCurrencies(HttpServletRequest servletRequest) {
        RestTemplate restTemplate = restTemplateSetup(servletRequest);
        String url = configurationManagementUrl + "/currencies";
        ParameterizedTypeReference<List<Currency>> responseType = new ParameterizedTypeReference<List<Currency>>() {
        };
        ResponseEntity<List<Currency>> currencies = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return currencies.getBody();
    }

    @Override
    public List<Gateway> getGateways(HttpServletRequest servletRequest) {
        RestTemplate restTemplate = restTemplateSetup(servletRequest);
        String url = configurationManagementUrl + "/gateways";
        ParameterizedTypeReference<List<Gateway>> responseType = new ParameterizedTypeReference<List<Gateway>>() {
        };
        ResponseEntity<List<Gateway>> gateways = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return gateways.getBody();
    }

    @Override
    public List<TdsMerchantType> getTdsMerchantTypes(HttpServletRequest servletRequest) {
        RestTemplate restTemplate = restTemplateSetup(servletRequest);
        String url = configurationManagementUrl + "/tds-merchant-types";
        ParameterizedTypeReference<List<TdsMerchantType>> responseType = new ParameterizedTypeReference<List<TdsMerchantType>>() {
        };
        ResponseEntity<List<TdsMerchantType>> tdsMerchantTypes = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return tdsMerchantTypes.getBody();
    }

    @Override
    public Page<GetInterfacesByMerchantIdResponse> getInterfacesByMerchantId(int page, int limit, long merchantId, HttpServletRequest servletRequest) {
        RestTemplate restTemplate = restTemplateSetup(servletRequest);
        String url = configurationManagementUrl + "/interfaces" + "/" + page + "/" + limit + "/" + merchantId;
        ParameterizedTypeReference<RestResponsePage<GetInterfacesByMerchantIdResponse>> responseType = new ParameterizedTypeReference<RestResponsePage<GetInterfacesByMerchantIdResponse>>() {
        };
        ResponseEntity<RestResponsePage<GetInterfacesByMerchantIdResponse>> interfaces = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        logger.info("This is the Interfaces Object that gets returned: " + interfaces.getBody());
        return interfaces.getBody();

    }

    @Override
    public Page<GetInterfacesByMerchantIdResponse> getInterfacesByApplicationId(int page, int limit, long applicationId, HttpServletRequest servletRequest) {
        RestTemplate restTemplate = restTemplateSetup(servletRequest);
        String url = configurationManagementUrl + "/interfaces" + "/" + page + "/" + limit + "/" + applicationId;
        ParameterizedTypeReference<RestResponsePage<GetInterfacesByMerchantIdResponse>> responseType = new ParameterizedTypeReference<RestResponsePage<GetInterfacesByMerchantIdResponse>>() {
        };
        ResponseEntity<RestResponsePage<GetInterfacesByMerchantIdResponse>> interfaces = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        logger.info("This is the Interfaces Object that gets returned: " + interfaces.getBody());
        return interfaces.getBody();
    }
}
