package za.co.wirecard.channel.backoffice.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import za.co.wirecard.channel.backoffice.config.ApplicationConstants;
import za.co.wirecard.channel.backoffice.config.UtilityService;
import za.co.wirecard.channel.backoffice.constants.ClientSearchEnum;
import za.co.wirecard.channel.backoffice.controllers.MerchantController;
import za.co.wirecard.channel.backoffice.dto.models.OnboardingSecondStepData;
import za.co.wirecard.channel.backoffice.dto.models.requests.MerchantDocument;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateMerchantRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformGetBillingDetailByMerchantId;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformUpdateMerchantRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.*;
import za.co.wirecard.channel.backoffice.dto.models.s3.GetObjectUrl;
import za.co.wirecard.channel.backoffice.dto.models.s3.MultipartFileUnique;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.exceptions.MerchantException;
import za.co.wirecard.channel.backoffice.models.MerchantClassification;
import za.co.wirecard.channel.backoffice.models.MerchantStatus;
import za.co.wirecard.channel.backoffice.models.RestResponsePage;
import za.co.wirecard.channel.backoffice.models.User;
import za.co.wirecard.channel.backoffice.repositories.*;
import com.amazonaws.services.s3.AmazonS3;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static za.co.wirecard.channel.backoffice.builder.MerchantSpecifications.*;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Value("${api.merchantmanagement.url}")
    private String merchantManagementUrl;

    @Value("${api.merchantmanagement.putMerchantUrl}")
    private String putMerchantManagementUrl;

    @Value("${api.usermanagement.url}")
    private String userManagementUrl;

//    @Value("${api.usermanagement.url.cancelMerchantUrl}")
//    private String cancelMerchantUrl;

    private static final Logger logger = LogManager.getLogger(MerchantService.class);
    private final UtilityService utilityService;
    private final RestTemplate restTemplate;
    private final MerchantRepository merchantRepository;
    private final ApplicationRepository applicationRepository;
    private final MerchantAddressRepository merchantAddressRepository;
    private final BillingDetailRepository billingDetailRepository;
    private final AuthUserRepository authUserRepository;
    private final BillingDetailService billingDetailService;
    private final S3BucketService s3BucketService;
    private final MerchantDocumentRepository merchantDocumentRepository;
    private final AuthUserMerchantRepository authUserMerchantRepository;

    public MerchantServiceImpl(UtilityService utilityService, RestTemplate restTemplate, MerchantRepository merchantRepository, ApplicationRepository applicationRepository, MerchantAddressRepository merchantAddressRepository, BillingDetailRepository billingDetailRepository, AuthUserRepository authUserRepository, BillingDetailService billingDetailService, S3BucketService s3BucketService, MerchantDocumentRepository merchantDocumentRepository, AuthUserMerchantRepository authUserMerchantRepository) {
        this.utilityService = utilityService;
        this.restTemplate = restTemplate;
        this.merchantRepository = merchantRepository;
        this.applicationRepository = applicationRepository;
        this.merchantAddressRepository = merchantAddressRepository;
        this.billingDetailRepository = billingDetailRepository;
        this.authUserRepository = authUserRepository;
        this.billingDetailService = billingDetailService;
        this.s3BucketService = s3BucketService;
        this.merchantDocumentRepository = merchantDocumentRepository;
        this.authUserMerchantRepository = authUserMerchantRepository;
    }

    @Override
    public Page<MerchantEntity> getMerchantsByFilter(int page, int limit, String stringCriteria, String stringSearch) {

        String merchantUid = null;
        String applicationUid = null;
        String clientName = null;
        String accountNo = null;

        //switch on stringCriteria
        if (stringSearch != null) {
            if (stringCriteria.equals(ClientSearchEnum.MERCHANT_UID.value())) {
                merchantUid = stringSearch;
            } else if (stringCriteria.equals(ClientSearchEnum.APPLICATION_UID.value())) {
                applicationUid = stringSearch;
            } else if (stringCriteria.equals(ClientSearchEnum.CLIENT_NAME.value())) {
                clientName = stringSearch;
            } else if (stringCriteria.equals(ClientSearchEnum.ACCOUNT_NO.value())) {
                accountNo = stringSearch;
            }
        }
        
        ApplicationEntity applicationEntity = null;
        if(applicationUid != null){
            applicationEntity = applicationRepository.findByApplicationUidEquals(applicationUid);
        }

        Specification<MerchantEntity> specification = Specification
                .where(nonOnboarding())
                .and(merchantUid == null || StringUtils.isBlank(merchantUid) ? null : merchantUidEquals(merchantUid))
                .and(accountNo == null || StringUtils.isBlank(accountNo) ? null : accountNoEquals(accountNo))
                .and(clientName == null || StringUtils.isBlank(clientName) ? null : clientNameLike(clientName))
                .and(applicationEntity == null && applicationUid != null ? merchantIdEquals(null) : applicationEntity == null ? null : merchantIdEquals(applicationEntity.getMerchantId()));
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("id")));
        Page<MerchantEntity> merchants = merchantRepository.findAll(specification, pageable);
        return merchants;
    }

    @Override
    public Page<MerchantEntity> getOnboardingMerchantsByFilter(int page, int limit, String stringCriteria, String stringSearch){
        String merchantUid = null;
        String clientName = null;
        String accountNo = null;

        //switch on stringCriteria
        if (stringSearch != null) {
            if (stringCriteria.equals(ClientSearchEnum.MERCHANT_UID.value())) {
                merchantUid = stringSearch;
            } else if (stringCriteria.equals(ClientSearchEnum.CLIENT_NAME.value())) {
                clientName = stringSearch;
            } else if (stringCriteria.equals(ClientSearchEnum.ACCOUNT_NO.value())) {
                accountNo = stringSearch;
            }
        }

        Specification<MerchantEntity> specification = Specification
                .where(isOnboarding())
                .and(merchantUid == null || StringUtils.isBlank(merchantUid) ? null : merchantUidEquals(merchantUid))
                .and(accountNo == null || StringUtils.isBlank(accountNo) ? null : accountNoEquals(accountNo))
                .and(clientName == null || StringUtils.isBlank(clientName) ? null : clientNameLike(clientName));
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("id")));
        Page<MerchantEntity> merchants = merchantRepository.findAll(specification, pageable);
        return merchants;
    }

    @Override
    public Page<PlatformGetMerchantsResponse> getMerchants(int page, int limit, String searchString) throws MerchantException {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(merchantManagementUrl)
                .queryParam("page", page)
                .queryParam("limit", limit)
                .queryParam(searchString != null ? "searchString" : "", searchString);
        logger.info("GET_MERCHANTS_URL" + uriComponentsBuilder.toUriString());
        ParameterizedTypeReference<RestResponsePage<PlatformGetMerchantsResponse>> responseType = new ParameterizedTypeReference<RestResponsePage<PlatformGetMerchantsResponse>>() {
        };
        ResponseEntity<RestResponsePage<PlatformGetMerchantsResponse>> merchants;
        try {
            merchants = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the Merchant Object that gets returned: " + merchants.getBody());
        return merchants.getBody();
    }

    @Override
    public PlatformGetMerchantByIdResponse getMerchant(long merchantId) throws MerchantException {
        String url = merchantManagementUrl + "/" + merchantId;
        logger.info("GET_MERCHANT_URL" + url);
        ResponseEntity<PlatformGetMerchantByIdResponse> merchant;
        try {
            merchant = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), PlatformGetMerchantByIdResponse.class);
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the Merchant Object that gets returned: " + merchant.getBody());
        return merchant.getBody();
    }

    @Override
    public PlatformCreateMerchantResponse createMerchant(PlatformCreateMerchantRequest merchant) throws MerchantException {
        String url = merchantManagementUrl;
        logger.info("ADD_MERCHANT_URL " + url + " MERCHANT " + merchant);
        ResponseEntity<PlatformCreateMerchantResponse> responseEntity;
        try {
            responseEntity = restTemplate.postForEntity(url, new HttpEntity<>(merchant, utilityService.getHttpHeaders()), PlatformCreateMerchantResponse.class);
            logger.info("MERCHANT_ADDED");
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return responseEntity.getBody();
    }

    @Override
    public void updateMerchant(long id, PlatformUpdateMerchantRequest merchant) throws MerchantException {
        String url = merchantManagementUrl + "/" + id;
        logger.info("EDIT_MERCHANT_URL " + url + " MERCHANT " + merchant);
        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(merchant, utilityService.getHttpHeaders()), Void.class);
            logger.info("MERCHANT_UPDATED");
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }

    }

    @Override
    public void deleteMerchant(long id) throws MerchantException {
        String url = merchantManagementUrl + "/" + id;
        logger.info("DELETE_MERCHANT_URL " + url);
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(null, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public List<MerchantClassification> getMerchantTypes() throws MerchantException {
        String url = merchantManagementUrl + "/classifications";
        logger.info("GET_MERCHANT_TYPES_URL" + url);
        ParameterizedTypeReference<List<MerchantClassification>> responseType = new ParameterizedTypeReference<List<MerchantClassification>>() {
        };
        ResponseEntity<List<MerchantClassification>> classifications;
        try {
            classifications = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the Merchant Classifications Object that gets returned: " + classifications.getBody());
        return classifications.getBody();
    }

    @Override
    public List<MerchantStatus> getMerchantStatuses() throws MerchantException {
        String url = merchantManagementUrl + "/statuses";
        logger.info("GET_MERCHANT_STATUSES_URL" + url);
        ParameterizedTypeReference<List<MerchantStatus>> responseType = new ParameterizedTypeReference<List<MerchantStatus>>() {
        };
        ResponseEntity<List<MerchantStatus>> statuses;
        try {
            statuses = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the Merchant Status Object that gets returned: " + statuses.getBody());
        return statuses.getBody();
    }

    @Override
    public List<User> getAccountManagers() throws MerchantException {
        String url = userManagementUrl + "/account-managers";
        logger.info("GET_ACCOUNT_MANAGERS_URL" + url);
        ParameterizedTypeReference<List<User>> responseType = new ParameterizedTypeReference<List<User>>() {
        };
        ResponseEntity<List<User>> accountManagers;
        try {
            accountManagers = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the Account Manager User Object that gets returned: " + accountManagers.getBody());
        return accountManagers.getBody();
    }

    @Override
    public List<User> getSalesPersons() throws MerchantException {
        String url = userManagementUrl + "/sales-persons";
        logger.info("GET_SALES_PERSONS_URL" + url);
        ParameterizedTypeReference<List<User>> responseType = new ParameterizedTypeReference<List<User>>() {
        };
        ResponseEntity<List<User>> salesPersons;
        try {
            salesPersons = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
        } catch (RestClientResponseException e){
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the Sales Person User Object that gets returned: " + salesPersons.getBody());
        return salesPersons.getBody();
    }

    @Override
    public List<User> getCreditControllers() throws MerchantException {
        String url = userManagementUrl + "/credit-controllers";
        logger.info("GET_CREDIT_CONTROLLERS_URL" + url);
        ParameterizedTypeReference<List<User>> responseType = new ParameterizedTypeReference<List<User>>() {
        };
        ResponseEntity<List<User>> creditControllers;
        try{
            creditControllers = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
        } catch (RestClientResponseException e){
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the Credit Controller User Object that gets returned: " + creditControllers.getBody());
        return creditControllers.getBody();
    }

    @Override
    public List<PlatformGetMerchantsResponse> getAllMerchants() throws MerchantException {
        return merchantRepository.findAllByOrderByCompanyNameAsc().stream().map(PlatformGetMerchantsResponse::new).collect(Collectors.toList());
    }

    @Override
    public PlatformCreateMerchantResponse editMerchant(PlatformCreateMerchantRequest merchant, long merchantId) {
        String url = putMerchantManagementUrl;
        logger.info("PUT_MERCHANT_URL " + url + " MERCHANT " + merchant + " FOR ID | " + String.format(url, merchantId));
        ResponseEntity<PlatformCreateMerchantResponse> responseEntity;
        try {
            responseEntity = restTemplate.exchange(String.format(url, merchantId), HttpMethod.PUT, new HttpEntity<>(merchant, utilityService.getHttpHeaders()), PlatformCreateMerchantResponse.class);
            logger.info("MERCHANT_EDITED");
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return responseEntity.getBody();
    }

    @Override
    public MerchantViewDetailResponse getMerchantViewDetail(long merchantId) {
        try {
            // Merchant
            MerchantEntity merchantEntity = merchantRepository.findById(merchantId)
                    .orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "No merchant exist for merchant id | " + merchantId));
            // Address
            Optional<MerchantAddressEntity> merchantAddressEntityPhysical = merchantAddressRepository.findByMerchantIdAndPostalAddressIsFalse(merchantId);
                    // .orElseThrow(() -> new GenericException("No phyiscal address found", HttpStatus.NOT_FOUND, "No phyiscal address exists for merchant id | " + merchantId));
            Optional<MerchantAddressEntity> merchantAddressEntityPostal = merchantAddressRepository.findByMerchantIdAndPostalAddressIsTrue(merchantId);
                    // .orElseThrow(() -> new GenericException("No postal address found", HttpStatus.NOT_FOUND, "No postal address exists for merchant id | " + merchantId));
            MerchantAddressResponse merchantAddressResponsePhysical = null;
            MerchantAddressResponse merchantAddressResponsePostal = null;
            if (merchantAddressEntityPhysical.isPresent()) {
                merchantAddressResponsePhysical = new MerchantAddressResponse(merchantAddressEntityPhysical.get());
            }
            if (merchantAddressEntityPostal.isPresent()) {
                merchantAddressResponsePostal = new MerchantAddressResponse(merchantAddressEntityPostal.get());
            }
            // Billing
            // BillingDetailEntity billingDetailEntity = billingDetailRepository.findByMerchantId(merchantId).orElseThrow(() -> new GenericException("No billing found", HttpStatus.NOT_FOUND, "No billing exist for merchant id | " + merchantId));
            PlatformGetBillingDetailByMerchantId platformGetBillingDetailByMerchantId = billingDetailService.getBillingDetails(merchantId);
//            if (platformGetBillingDetailByMerchantId == null) {
//                throw new GenericException("No billing detail found", HttpStatus.NOT_FOUND, "No primary contact exist for merchant id | " + merchantId);
//            }
            OnboardingSecondStepData onboardingSecondStepData = null;
            if (platformGetBillingDetailByMerchantId != null) {
                onboardingSecondStepData = new OnboardingSecondStepData(platformGetBillingDetailByMerchantId);
            }
            // Primary contact
            Optional<AuthUserMerchantEntity> authUserMerchantEntity = authUserMerchantRepository.findByMerchantIdAndPrimaryContactIsTrue(merchantId);
            AuthUserEntity authUserEntity = null;
            if (authUserMerchantEntity.isPresent()) {
                        // .orElseThrow(() -> new GenericException("No primary contact found", HttpStatus.NOT_FOUND, "No primary contact exist for merchant id | " + merchantId));
                authUserEntity = authUserRepository.findById(authUserMerchantEntity.get().getAuthUserId()).get();
            }

            MerchantViewDetailResponse merchantViewDetailResponse = new MerchantViewDetailResponse(merchantEntity, merchantAddressResponsePhysical, merchantAddressResponsePostal, onboardingSecondStepData, authUserEntity);
            return merchantViewDetailResponse;
        } catch (RestClientResponseException e) {
            throw new GenericException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public List<MerchantDocument> postMerchantDocuments(MultipartFileUnique multipartFileUnique, long merchantId) {
        try {
            MerchantEntity merchantEntity = merchantRepository.findById(merchantId)
                    .orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "Could not find merchant with id | " + merchantId));
            // Upload to S3 bucket
            GetObjectUrl objectUrl = s3BucketService.uploadFileToS3Bucket(multipartFileUnique);
            String s3BucketObjectName = getS3Client(multipartFileUnique);
            logger.info("POTENTIAL S3 NAME | " + s3BucketObjectName);
            MerchantDocumentEntity merchantDocumentEntity = new MerchantDocumentEntity();
            // Check if merchant document entity exists
            Optional<MerchantDocumentEntity> merchantDocumentEntityOptional = merchantDocumentRepository.findByLink(s3BucketObjectName);
            if (merchantDocumentEntityOptional.isPresent()) {
                // If it already exists, assign variable to it
                merchantDocumentEntity = merchantDocumentEntityOptional.get();
                logger.info("Object exists" + merchantDocumentEntity.getLink());
            }
            merchantDocumentEntity.setLink(objectUrl.getUrl().toString());
            merchantDocumentEntity.setName(objectUrl.getName());
            merchantDocumentEntity.setMerchantByMerchantId(merchantEntity);
            MerchantDocumentEntity merchantDocumentEntity1 = merchantDocumentRepository.save(merchantDocumentEntity);
            logger.info("Object exists" + merchantDocumentEntity.getLink());
            List<MerchantDocumentEntity> merchantDocumentEntities =  merchantDocumentRepository.findAllByMerchantByMerchantId(merchantEntity)
                    .orElseThrow(() -> new GenericException("No documents found", HttpStatus.NOT_FOUND, "Could not find documents belong to merchant id | " + merchantId));

            return
                 merchantDocumentEntities
                .stream()
                .map(MerchantDocument::new)
                .collect(Collectors.toList());

        } catch (RestClientResponseException | IOException e) {
            throw new GenericException(e.getMessage(), HttpStatus.valueOf(e.getMessage()), e.getLocalizedMessage());
        }
    }

    @Override
    public List<MerchantDocument> getMerchantDocuments(long merchantId) {
        MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "Could not find merchant with id | " + merchantId));
        return merchantDocumentRepository.
                findAllByMerchantByMerchantId(merchantEntity)
                    .orElseThrow(() -> new GenericException("No documents found", HttpStatus.NOT_FOUND, "Could not find documents belong to merchant id | " + merchantId))
                .stream()
                .map(MerchantDocument::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<MerchantDocument> deleteMerchantDocuments(long id, long merchantId) {
        MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "Could not find merchant with id | " + merchantId));
        MerchantDocumentEntity merchantDocumentEntity = merchantDocumentRepository.findById(id).orElseThrow(() -> new GenericException("No document found", HttpStatus.NOT_FOUND, "Could not find document with id | " + id));
        this.merchantDocumentRepository.delete(merchantDocumentEntity);
        // Delete from S3 Bucket
        s3BucketService.deleteFileFromS3Bucket(merchantDocumentEntity.getName());
        // Return new list
        return merchantDocumentRepository.
                findAllByMerchantByMerchantId(merchantEntity)
                .orElseThrow(() -> new GenericException("No documents found", HttpStatus.NOT_FOUND, "Could not find documents belong to merchant id | " + merchantId))
                .stream()
                .map(MerchantDocument::new)
                .collect(Collectors.toList());
    }

//    @Override
//    public void cancelMerchant(long merchantId, long userId) {
//        String url = cancelMerchantUrl;
//        //logger.info("PUT_MERCHANT_URL " + url + " MERCHANT " + merchant + " FOR ID | " + String.format(url, merchantId));
//        try {
//            restTemplate.put(String.format(url, merchantId, userId), HttpMethod.PUT, new HttpEntity<>(null, utilityService.getHttpHeaders()));
//            logger.info("MERCHANT_CANCELLED");
//        } catch (RestClientResponseException e) {
//            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
//        }
//    }

    private String getS3Client(MultipartFileUnique multipartFileUnique) {
        Regions clientRegion = Regions.AF_SOUTH_1;
        String bucketName = ApplicationConstants.ADUMO_S3_BUCKET_NAME.value();
        // LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "/" +
        String fileObjKeyName =  multipartFileUnique.getImage().getOriginalFilename();
        // File file = new File(putObjectS3Bucket.getMultipartFileUnique());

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();

        return s3Client.getUrl(bucketName, fileObjKeyName).toString();
    }

}
