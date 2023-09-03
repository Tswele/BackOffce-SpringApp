package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import za.co.wirecard.channel.backoffice.config.UtilityService;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateBillingDetailRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformGetBillingDetailByMerchantId;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformPutBillingDetail;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateBillingDetailResponse;
import za.co.wirecard.channel.backoffice.exceptions.BillingDetailException;
import za.co.wirecard.channel.backoffice.models.*;

import java.util.List;

@Service
public class BillingDetailServiceImpl implements BillingDetailService {

    private static final Logger logger = LogManager.getLogger(BillingDetailService.class);

    @Value("${api.billingmanagement.url}")
    private String billingManagementUrl;

    @Value("${api.billingmanagement.putBillingManagementUrl}")
    private String putBillingManagementUrl;

    @Value("${api.configurationmanagement.url}")
    private String configurationManagementUrl;

    private final RestTemplate restTemplate;
    private final UtilityService utilityService;

    public BillingDetailServiceImpl(RestTemplate restTemplate, UtilityService utilityService) {
        this.restTemplate = restTemplate;
        this.utilityService = utilityService;
    }

    @Override
    public List<PaymentType> getPaymentTypes() throws BillingDetailException {
        String url = configurationManagementUrl + "/payment/types";
        logger.info("GET_PAYMENT_TYPES_URL: " + url);
        ParameterizedTypeReference<List<PaymentType>> responseType = new ParameterizedTypeReference<List<PaymentType>>() {
        };
        ResponseEntity<List<PaymentType>> paymentTypes;
        try {
            paymentTypes = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
            logger.info("PAYMENT_TYPES_RETURNED: " + paymentTypes.getBody());
        } catch (RestClientResponseException e) {
            throw new BillingDetailException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return paymentTypes.getBody();
    }

    @Override
    public List<Bank> getBanks() throws BillingDetailException {
        String url = billingManagementUrl + "/banks";
        logger.info("GET_BANKS_URL: " + url);
        ParameterizedTypeReference<List<Bank>> responseType = new ParameterizedTypeReference<List<Bank>>() {
        };
        ResponseEntity<List<Bank>> banks;
        try {
            banks = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
            logger.info("BANKS_RETURNED: " + banks.getBody());
        } catch (RestClientResponseException e) {
            throw new BillingDetailException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return banks.getBody();
    }

    @Override
    public List<BankBranchCode> getBankBranchCodes(long bankId) throws BillingDetailException {
        String url = billingManagementUrl + "/bank-branch-codes/" + bankId;//?bankId="+bankId;
        logger.info("GET_BANK_BRANCH_CODES_URL: " + url);
        ParameterizedTypeReference<List<BankBranchCode>> responseType = new ParameterizedTypeReference<List<BankBranchCode>>() {
        };
        ResponseEntity<List<BankBranchCode>> bankBranchCodes;
        try {
            bankBranchCodes = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
            logger.info("BANK_BRANCH_CODES_RETURNED :" + bankBranchCodes.getBody());
        } catch (RestClientResponseException e) {
            throw new BillingDetailException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return bankBranchCodes.getBody();
    }

    @Override
    public List<BankAccountType> getAccountTypes() {
        String url = billingManagementUrl + "/bank-account-types";
        logger.info("GET_ACCOUNT_TYPES_URL: " + url);
        ParameterizedTypeReference<List<BankAccountType>> responseType = new ParameterizedTypeReference<List<BankAccountType>>() {
        };
        ResponseEntity<List<BankAccountType>> bankAccountTypes;
        try {
            bankAccountTypes = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
            logger.info("ACCOUNT_TYPES_RETURNED: " + bankAccountTypes.getBody());
        } catch (RestClientResponseException e) {
            throw new BillingDetailException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return bankAccountTypes.getBody();
    }

    @Override
    public List<RateStructure> getRateStructures() {
        String url = billingManagementUrl + "/rate-structures";
        logger.info("GET_RATE_STRUCTURES_URL: " + url);
        ParameterizedTypeReference<List<RateStructure>> responseType = new ParameterizedTypeReference<List<RateStructure>>() {
        };
        ResponseEntity<List<RateStructure>> rateStructures;
        try {
            rateStructures = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
            logger.info("RATE_STRUCTURES_RETURNED: " + rateStructures.getBody());
        } catch (RestClientResponseException e) {
            throw new BillingDetailException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return rateStructures.getBody();
    }

    @Override
    public PlatformGetBillingDetailByMerchantId getBillingDetails(long id) {
        String url = billingManagementUrl + "/" + id;
        logger.info("GET_BILLING_DETAILS_URL: " + url);
        ResponseEntity<PlatformGetBillingDetailByMerchantId> billingDetail;
        try {
            billingDetail = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), PlatformGetBillingDetailByMerchantId.class);
            logger.info("BILLING_DETAILS_RETURNED: "+billingDetail.getBody());
        } catch (RestClientResponseException e) {
            // throw new BillingDetailException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
            return null;
        }
        return billingDetail.getBody();
    }

    @Override
    public PlatformCreateBillingDetailResponse createBillingDetails(PlatformCreateBillingDetailRequest billingDetail) throws BillingDetailException {
        String url = billingManagementUrl;
        logger.info("Add Billing Detail " + " URL " + url + " BILLING DETAIL " + billingDetail.getRateStructureId());
        ResponseEntity<PlatformCreateBillingDetailResponse> billingDetailResponse;
        try {
            billingDetailResponse = restTemplate.postForEntity(url, new HttpEntity<>(billingDetail, utilityService.getHttpHeaders()), PlatformCreateBillingDetailResponse.class);
            logger.info("BILLING_DETAILS_ADDED");
        }catch (RestClientResponseException e) {
            throw new BillingDetailException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return billingDetailResponse.getBody();
    }

    @Override
    public void editBillingDetails(long id, PlatformPutBillingDetail billingDetail) throws BillingDetailException {
        String url = billingManagementUrl + "/" + id;
        logger.info("BILLING_DETAILS_URL: " + url + " BILLING DETAIL " + billingDetail);
        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(billingDetail, utilityService.getHttpHeaders()), Void.class);
            logger.info("BILLING_DETAILS_EDITED: " + id);
        } catch (RestClientResponseException e) {
            throw new BillingDetailException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public void deleteBillingDetails(long id) {
        String url = billingManagementUrl + "/" + id;
        logger.info("Delete Billing Detail " + " URL " + url);
        restTemplate.delete(url);
    }

    @Override
    public PlatformCreateBillingDetailResponse editBillingDetailsOnboarding(PlatformCreateBillingDetailRequest billingDetail, long merchantId) {
        logger.info("Edit Billing Detail " + " URL " + String.format(putBillingManagementUrl, merchantId) + " BILLING DETAIL " + billingDetail.getRateStructureId());
        ResponseEntity<PlatformCreateBillingDetailResponse> billingDetailResponse;
        try {
            billingDetailResponse = restTemplate.exchange(String.format(putBillingManagementUrl, merchantId), HttpMethod.PUT, new HttpEntity<>(billingDetail, utilityService.getHttpHeaders()), PlatformCreateBillingDetailResponse.class);
            logger.info("BILLING_DETAILS_EDITED");
        }catch (RestClientResponseException e) {
            throw new BillingDetailException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return billingDetailResponse.getBody();
    }
}
