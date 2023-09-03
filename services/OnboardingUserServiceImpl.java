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
import za.co.wirecard.channel.backoffice.dto.models.IncompleteOnboardingData;
import za.co.wirecard.channel.backoffice.dto.models.OnboardingSecondStepData;
import za.co.wirecard.channel.backoffice.dto.models.responses.MerchantAddressResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateAuthUserResponse;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.exceptions.MerchantException;
import za.co.wirecard.channel.backoffice.models.OnboardingAdminGroup;
import za.co.wirecard.channel.backoffice.models.OnboardingAdminGroupResponse;
import za.co.wirecard.channel.backoffice.models.requests.CreateAdminUserRequest;
import za.co.wirecard.channel.backoffice.repositories.*;

import java.util.Optional;

@Service
public class OnboardingUserServiceImpl implements OnboardingUserService{

    @Value("${api.contactmanagement.url}")
    private String contactManagementUrl;

    @Value("${api.contactmanagement.putContactManagementUrl}")
    private String putContactManagementUrl;

    @Value("${api.admingroupmanagement.url}")
    private String adminGroupManagementUrl;

    @Value("${api.addressmanagement.onboarding-done}")
    private String onboardingUrl;

    private static final Logger logger = LogManager.getLogger(OnboardingUserServiceImpl.class);

    private final RestTemplate restTemplate;
    private final UtilityService utilityService;
    private final MerchantRepository merchantRepository;
    private final MerchantAddressRepository merchantAddressRepository;
    private final BillingDetailRepository billingDetailRepository;
    private final MerchantOnboardingRepository merchantOnboardingRepository;
    private final AuthUserRepository authUserRepository;

    public OnboardingUserServiceImpl(RestTemplate restTemplate,
                                     UtilityService utilityService,
                                     MerchantRepository merchantRepository,
                                     MerchantAddressRepository merchantAddressRepository,
                                     BillingDetailRepository billingDetailRepository, MerchantOnboardingRepository merchantOnboardingRepository, AuthUserRepository authUserRepository) {
        this.restTemplate = restTemplate;
        this.utilityService = utilityService;
        this.merchantRepository = merchantRepository;
        this.merchantAddressRepository = merchantAddressRepository;
        this.billingDetailRepository = billingDetailRepository;
        this.merchantOnboardingRepository = merchantOnboardingRepository;
        this.authUserRepository = authUserRepository;
    }

    public OnboardingAdminGroupResponse createAdminGroup(OnboardingAdminGroup onboardingAdminGroup) {
        String url = adminGroupManagementUrl;
        ResponseEntity<OnboardingAdminGroupResponse> responseEntity;
        try {
            responseEntity = restTemplate.postForEntity(url, new HttpEntity<>(onboardingAdminGroup, utilityService.getHttpHeaders()), OnboardingAdminGroupResponse.class);
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return responseEntity.getBody();
    }

    public PlatformCreateAuthUserResponse createAdminUser(CreateAdminUserRequest createAdminUserRequest) {
        String url = contactManagementUrl;
        ResponseEntity<PlatformCreateAuthUserResponse> responseEntity;
        try {
            responseEntity = restTemplate.postForEntity(url, new HttpEntity<>(createAdminUserRequest, utilityService.getHttpHeaders()), PlatformCreateAuthUserResponse.class);
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return responseEntity.getBody();
    }

    public IncompleteOnboardingData getIncompleteOnboardingData(long merchantId) {
        Optional<MerchantOnboardingEntity> merchantOnboardingEntity = merchantOnboardingRepository.findByMerchantId(merchantId);
        if (!merchantOnboardingEntity.isPresent()) {
            throw new GenericException("No merchant found", HttpStatus.NOT_FOUND, "No merchant exist for merchant id | " + merchantId);
        }
        MerchantOnboardingEntity merchantOnboardingEntityObj = merchantOnboardingEntity.get();
        Optional<MerchantEntity> onboardingMerchantEntity = Optional.empty();
        Optional<MerchantAddressEntity> onboardingPhyiscalAddress = Optional.empty();
        Optional<MerchantAddressEntity> onboardingPostalAddress = Optional.empty();
        Optional<BillingDetailEntity> billingDetailEntity = Optional.empty();
        Optional<AuthUserEntity> authUserEntity = Optional.empty();
        if (merchantOnboardingEntity.get().getMerchantId() != null) {
            onboardingMerchantEntity = merchantRepository.findById(merchantOnboardingEntityObj.getMerchantId());
        }
        if (merchantOnboardingEntity.get().getMerchantPhysicalAddressId() != null) {
            onboardingPhyiscalAddress = merchantAddressRepository.findById(merchantOnboardingEntityObj.getMerchantPhysicalAddressId());
        }
        if (merchantOnboardingEntity.get().getMerchantPostalAddressId() != null) {
            onboardingPostalAddress = merchantAddressRepository.findById(merchantOnboardingEntityObj.getMerchantPostalAddressId());
        }
        if (merchantOnboardingEntity.get().getBillingDetailId() != null) {
            billingDetailEntity = billingDetailRepository.findById(merchantOnboardingEntityObj.getBillingDetailId());
        }
        if (merchantOnboardingEntity.get().getAdminContactId() != null) {
            authUserEntity = authUserRepository.findById(merchantOnboardingEntityObj.getAdminContactId());
        }
        OnboardingSecondStepData onboardingSecondStepData = null;
        if (billingDetailEntity.isPresent()) {
            onboardingSecondStepData = new OnboardingSecondStepData(billingDetailEntity.get());
        }

        MerchantAddressResponse merchantAddressResponsePhysical = onboardingPhyiscalAddress.map(MerchantAddressResponse::new).orElse(null);
        MerchantAddressResponse merchantAddressResponsePostal = onboardingPostalAddress.map(MerchantAddressResponse::new).orElse(null);

        return new IncompleteOnboardingData(
                onboardingMerchantEntity.isPresent() ? onboardingMerchantEntity.orElse(null) : null,
                merchantAddressResponsePhysical,
                merchantAddressResponsePostal,
                onboardingSecondStepData,
                authUserEntity != null && authUserEntity.isPresent() ? authUserEntity.orElse(null) : null
        );
    }

    @Override
    public void updateMerchantToOnboarded(Long id) {
        String url = onboardingUrl;
        try {
            restTemplate.exchange(String.format(url, id), HttpMethod.PUT, new HttpEntity<>(null, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public PlatformCreateAuthUserResponse editAdminUser(CreateAdminUserRequest createAdminUserRequest, long merchantId) {
        ResponseEntity<PlatformCreateAuthUserResponse> responseEntity;
        try {
            responseEntity = restTemplate.exchange(String.format(putContactManagementUrl, merchantId), HttpMethod.PUT, new HttpEntity<>(createAdminUserRequest, utilityService.getHttpHeaders()), PlatformCreateAuthUserResponse.class);
        } catch (RestClientResponseException e) {
            throw new MerchantException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return responseEntity.getBody();
    }
}
