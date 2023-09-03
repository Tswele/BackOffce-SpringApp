package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import za.co.wirecard.channel.backoffice.config.UtilityService;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateAddressRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformGetAddressesByMerchantIdResponse;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformPutAddressesByMerchantId;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateAddressResponse;
import za.co.wirecard.channel.backoffice.exceptions.MerchantAddressException;

@Service
public class AddressServiceImpl implements AddressService {

    @Value("${api.addressmanagement.url}")
    private String addressManagementUrl;

    @Value("${api.addressmanagement.putAddressUrl}")
    private String putAddressManagementUrl;

    private static final Logger logger = LogManager.getLogger(AddressServiceImpl.class);

    private final RestTemplate restTemplate;
    private final UtilityService utilityService;

    public AddressServiceImpl(RestTemplate restTemplate, UtilityService utilityService) {
        this.restTemplate = restTemplate;
        this.utilityService = utilityService;
    }

    @Override
    public PlatformGetAddressesByMerchantIdResponse getAddress(long merchantId) throws MerchantAddressException {
        String url = addressManagementUrl + "/addresses/?merchantId=" + merchantId;
        ResponseEntity<PlatformGetAddressesByMerchantIdResponse> address;
        try {
            address = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), PlatformGetAddressesByMerchantIdResponse.class);
        } catch (RestClientResponseException e) {
            throw new MerchantAddressException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the Address Object that gets returned: " + address.getBody());
        return address.getBody();
    }

    @Override
    public PlatformCreateAddressResponse createAddress(PlatformCreateAddressRequest address) throws MerchantAddressException {
        String url = addressManagementUrl + "/addresses";
        ResponseEntity<PlatformCreateAddressResponse> addressSaved;
        try {
            addressSaved = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(address, utilityService.getHttpHeaders()), PlatformCreateAddressResponse.class);
        } catch (RestClientResponseException e){
            throw new MerchantAddressException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return addressSaved.getBody();
    }

    @Override
    public void editAddress(PlatformPutAddressesByMerchantId address, long merchantId) throws MerchantAddressException {
        String url = addressManagementUrl + "/addresses/" + merchantId;
        try {
            logger.info("Edit Address " + " URL " + url + " ADDRESS " + address);
            logger.info("CITY_ID: " + address.getResidentialAddress().getCityId());
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(address, utilityService.getHttpHeaders()),Void.class);
        } catch (RestClientResponseException e){
            throw new MerchantAddressException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public void deleteAddress(long merchantId) throws MerchantAddressException {
        String url = addressManagementUrl + "/addresses/" + merchantId;
        try{
            logger.info("Edit Address " + " URL " + url);
            restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(null, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e){
            throw new MerchantAddressException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public PlatformCreateAddressResponse editAddressOnboarding(PlatformCreateAddressRequest address, long merchantId) {
        ResponseEntity<PlatformCreateAddressResponse> addressSaved;
        try {
            addressSaved = restTemplate.exchange(String.format(putAddressManagementUrl, merchantId), HttpMethod.PUT, new HttpEntity<>(address, utilityService.getHttpHeaders()), PlatformCreateAddressResponse.class);
        } catch (RestClientResponseException e){
            throw new MerchantAddressException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return addressSaved.getBody();
    }
}
