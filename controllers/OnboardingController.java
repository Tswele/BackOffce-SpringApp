package za.co.wirecard.channel.backoffice.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateAddressRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateBillingDetailRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateMerchantRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateAddressResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateAuthUserResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateBillingDetailResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateMerchantResponse;
import za.co.wirecard.channel.backoffice.entities.BankBranchCodeEntity;
import za.co.wirecard.channel.backoffice.entities.BankEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantOnboardingEntity;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.OnboardingAdminGroup;
import za.co.wirecard.channel.backoffice.models.OnboardingAdminGroupResponse;
import za.co.wirecard.channel.backoffice.models.requests.CreateAdminUserRequest;
import za.co.wirecard.channel.backoffice.repositories.*;
import za.co.wirecard.channel.backoffice.services.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/onboarding")
public class OnboardingController {

    private static final Logger logger = LogManager.getLogger(OnboardingController.class);

    private final MerchantService merchantService;
    private final CountryService countryService;
    private final BackOfficeUserRepository backOfficeUserRepository;
    private final BillingDetailService billingDetailService;
    private final AddressService addressService;
    private final OnboardingUserService onboardingUserService;
    private final BankBranchCodeRepository bankBranchCodeRepository;
    private final MerchantOnboardingRepository merchantOnboardingRepository;
    private final BankRepository bankRepository;
    private final MerchantRepository merchantRepository;

    public OnboardingController(MerchantService merchantService,
                                CountryService countryService,
                                BackOfficeUserRepository backOfficeUserRepository,
                                BillingDetailService billingDetailService,
                                AddressService addressService,
                                OnboardingUserService onboardingUserService, BankBranchCodeRepository bankBranchCodeRepository, MerchantOnboardingRepository merchantOnboardingRepository, BankRepository bankRepository, MerchantRepository merchantRepository) {
        this.merchantService = merchantService;
        this.countryService = countryService;
        this.backOfficeUserRepository = backOfficeUserRepository;
        this.billingDetailService = billingDetailService;
        this.addressService = addressService;
        this.onboardingUserService = onboardingUserService;
        this.bankBranchCodeRepository = bankBranchCodeRepository;
        this.merchantOnboardingRepository = merchantOnboardingRepository;
        this.bankRepository = bankRepository;
        this.merchantRepository = merchantRepository;
    }

    @GetMapping("/step1")
    public ResponseEntity<OnboardingFirstStep> getFirstStepData() {
        OnboardingFirstStep onboardingFirstStep = new OnboardingFirstStep();
        onboardingFirstStep.setMerchantClassificationList(merchantService.getMerchantTypes());
        onboardingFirstStep.setMerchantStatusList(merchantService.getMerchantStatuses());
        onboardingFirstStep.setCountryList(countryService.getCountries());
        onboardingFirstStep.setAccountManagers(backOfficeUserRepository.findAllByIsAccountManagerIsTrue());
        onboardingFirstStep.setSalesPersons(backOfficeUserRepository.findAllByIsSalesPersonIsTrue());
        return ResponseEntity.ok(onboardingFirstStep);
    }

    @PostMapping("/step1")
    public ResponseEntity<?> postFirstStepData(@RequestBody OnboardingFirstStepData onboardingFirstStepData, HttpServletRequest servletRequest) {
        //create merchant
        PlatformCreateMerchantRequest platformCreateMerchantRequest = new PlatformCreateMerchantRequest();
        platformCreateMerchantRequest.setMerchantName(onboardingFirstStepData.getOnboardingMerchantData().getCompanyName());
        platformCreateMerchantRequest.setTradingAs(onboardingFirstStepData.getOnboardingMerchantData().getTradingAs());
        platformCreateMerchantRequest.setCompanyRegNo(onboardingFirstStepData.getOnboardingMerchantData().getCompanyRegNo());
        platformCreateMerchantRequest.setVatNumber(onboardingFirstStepData.getOnboardingMerchantData().getVatNo());
        platformCreateMerchantRequest.setMerchantClassificationId(onboardingFirstStepData.getOnboardingMerchantData().getMerchantClassificationId());
        platformCreateMerchantRequest.setWebsite(onboardingFirstStepData.getOnboardingMerchantData().getWebsite());
        platformCreateMerchantRequest.setAccountManagerId(onboardingFirstStepData.getOnboardingMerchantData().getAccountManagerId());
        platformCreateMerchantRequest.setSalesPersonId(onboardingFirstStepData.getOnboardingMerchantData().getSalesPersonId());
        platformCreateMerchantRequest.setCreatedBy(getUserFromSession(servletRequest));
        //contains merchant Id to be returned
        PlatformCreateMerchantResponse platformCreateMerchantResponse = merchantService.createMerchant(platformCreateMerchantRequest);

        //create physical address
        PlatformCreateAddressRequest platformCreateAddressRequest1 = new PlatformCreateAddressRequest();
        platformCreateAddressRequest1.setAddressLine1(onboardingFirstStepData.getPhysicalAddressData().getAddressLine1());
        platformCreateAddressRequest1.setAddressLine2(onboardingFirstStepData.getPhysicalAddressData().getAddressLine2());
        platformCreateAddressRequest1.setAddressLine3(onboardingFirstStepData.getPhysicalAddressData().getAddressLine3());
        platformCreateAddressRequest1.setAddressLine4(onboardingFirstStepData.getPhysicalAddressData().getAddressLine4());
        platformCreateAddressRequest1.setMerchantId(platformCreateMerchantResponse.getMerchantId());
        platformCreateAddressRequest1.setCityId(onboardingFirstStepData.getPhysicalAddressData().getCityId());
        platformCreateAddressRequest1.setPostalCode(onboardingFirstStepData.getPhysicalAddressData().getPostalCode());
        platformCreateAddressRequest1.setPostalAddress(false);
        PlatformCreateAddressResponse phyiscalAddress = addressService.createAddress(platformCreateAddressRequest1);

        //create postal address
        PlatformCreateAddressRequest platformCreateAddressRequest2 = new PlatformCreateAddressRequest();
        platformCreateAddressRequest2.setAddressLine1(onboardingFirstStepData.getPostalAddressData().getAddressLine1());
        platformCreateAddressRequest2.setAddressLine2(onboardingFirstStepData.getPostalAddressData().getAddressLine2());
        platformCreateAddressRequest2.setAddressLine3(onboardingFirstStepData.getPostalAddressData().getAddressLine3());
        platformCreateAddressRequest2.setAddressLine4(onboardingFirstStepData.getPostalAddressData().getAddressLine4());
        platformCreateAddressRequest2.setMerchantId(platformCreateMerchantResponse.getMerchantId());
        platformCreateAddressRequest2.setCityId(onboardingFirstStepData.getPostalAddressData().getCityId());
        platformCreateAddressRequest2.setPostalCode(onboardingFirstStepData.getPostalAddressData().getPostalCode());
        platformCreateAddressRequest2.setPostalAddress(true);
        PlatformCreateAddressResponse postalAddress = addressService.createAddress(platformCreateAddressRequest2);

        // Save to onboarding entity................................
        MerchantOnboardingEntity merchantOnboardingEntity = new MerchantOnboardingEntity();
        merchantOnboardingEntity.setMerchantId(platformCreateMerchantResponse.getMerchantId());
        merchantOnboardingEntity.setMerchantPhysicalAddressId(phyiscalAddress.getId());
        merchantOnboardingEntity.setMerchantPostalAddressId(postalAddress.getId());
        logger.info("onboarding save |" + merchantOnboardingEntity.toString());
        merchantOnboardingRepository.save(merchantOnboardingEntity);

        return ResponseEntity.ok(platformCreateMerchantResponse);
    }

    private Long getUserFromSession(HttpServletRequest servletRequest) {
        String accessToken = servletRequest.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        if (accessToken == null) {
            throw new GenericException("Token information service was not passed proper parameters", HttpStatus.BAD_REQUEST, "Token derived object is null");
        }
        JWT jwt = new JWT();
        DecodedJWT decodedJWT = jwt.decodeJwt(accessToken);
        Long userId = decodedJWT.getClaims().get("userId") != null ? decodedJWT.getClaims().get("userId").asLong() : null;
        return userId;
    }

    @GetMapping("/step2")
    public ResponseEntity<OnboardingSecondStep> getSecondStepData() {
        OnboardingSecondStep onboardingSecondStep = new OnboardingSecondStep();
        onboardingSecondStep.setBankAccountTypeList(billingDetailService.getAccountTypes());
        onboardingSecondStep.setBankList(billingDetailService.getBanks());
        onboardingSecondStep.setPaymentTypeList(billingDetailService.getPaymentTypes());
        onboardingSecondStep.setRateStructureList(billingDetailService.getRateStructures());
        onboardingSecondStep.setCreditControllers(backOfficeUserRepository.findAllByIsCreditControllerIsTrue());
        return ResponseEntity.ok(onboardingSecondStep);
    }

    @GetMapping("/step2/branch-codes/{bankId}")
    public ResponseEntity<List<BankBranchCodeEntity>> getSecondStepDataBranchCodes(@PathVariable long bankId) {
        BankEntity bankEntity = bankRepository.findById(bankId).orElseThrow(() -> new GenericException("No bank found", HttpStatus.NOT_FOUND, "No bank exist for bank id | " + bankId));
        List<BankBranchCodeEntity> bankBranchCodeEntity = bankBranchCodeRepository.findAllByBankByBankId(bankEntity).orElseThrow(() -> new GenericException("No branch code found", HttpStatus.NOT_FOUND, "No branch codes exist for bank id | " + bankId));
        return ResponseEntity.ok(bankBranchCodeEntity);
    }

    @PostMapping("/step2/{merchantId}")
    public ResponseEntity<?> postSecondStepData(@RequestBody OnboardingSecondStepData onboardingSecondStepData, @PathVariable long merchantId) {
        PlatformCreateBillingDetailRequest platformCreateBillingDetailRequest = new PlatformCreateBillingDetailRequest();
        platformCreateBillingDetailRequest.setMerchantId(onboardingSecondStepData.getMerchantId());
        platformCreateBillingDetailRequest.setPaymentMethodId(onboardingSecondStepData.getPaymentTypeId());
        platformCreateBillingDetailRequest.setCreditControllerId(onboardingSecondStepData.getCreditControllerId());
        platformCreateBillingDetailRequest.setRateStructureId(onboardingSecondStepData.getRateStructureId());
        platformCreateBillingDetailRequest.setInvoiceDay(onboardingSecondStepData.getDeductionDay());
        platformCreateBillingDetailRequest.setBankId(onboardingSecondStepData.getBankId());
        platformCreateBillingDetailRequest.setBranchCodeId(onboardingSecondStepData.getBranchCodeId());
        platformCreateBillingDetailRequest.setBankAccountNumber(onboardingSecondStepData.getAccountNo());
        platformCreateBillingDetailRequest.setBankAccountHolder(onboardingSecondStepData.getAccountHolder());
        platformCreateBillingDetailRequest.setAccountTypeId(onboardingSecondStepData.getAccountTypeId());
        platformCreateBillingDetailRequest.setXeroTenantId(onboardingSecondStepData.getXeroTenantId());
        platformCreateBillingDetailRequest.setXeroContactId(onboardingSecondStepData.getXeroContactId());
        if (onboardingSecondStepData.getInvoiceDueDateOffset() != null) {
            platformCreateBillingDetailRequest.setInvoiceDueDateOffset(onboardingSecondStepData.getInvoiceDueDateOffset());
        }
        PlatformCreateBillingDetailResponse platformCreateBillingDetailResponse = billingDetailService.createBillingDetails(platformCreateBillingDetailRequest);

        // Save to onboarding entity................................
        // MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "No merchant exist for merchant id | " + merchantId));
        MerchantOnboardingEntity merchantOnboardingEntity = merchantOnboardingRepository.findByMerchantId(merchantId).orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "No merchant exist for merchant id | " + merchantId));;
        merchantOnboardingEntity.setBillingDetailId(platformCreateBillingDetailResponse.getId());

        merchantOnboardingRepository.save(merchantOnboardingEntity);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/step3/{merchantId}")
    public ResponseEntity<?> postThirdStepData(@RequestBody OnboardingThirdStepData onboardingThirdStepData, @PathVariable long merchantId, HttpServletRequest request) {
        OnboardingAdminGroup onboardingAdminGroup = new OnboardingAdminGroup();
        onboardingAdminGroup.setMerchantId(onboardingThirdStepData.getMerchantId());
        onboardingAdminGroup.setCreatedBy(onboardingThirdStepData.getCreatedBy());
        onboardingAdminGroup.setName("Admin");
        onboardingAdminGroup.setCode("Admin");
        onboardingAdminGroup.setDescription("The Admin permission group which contains all permissions.");
        OnboardingAdminGroupResponse onboardingAdminGroupResponse = onboardingUserService.createAdminGroup(onboardingAdminGroup);
        CreateAdminUserRequest createAdminUserRequest = new CreateAdminUserRequest();
        createAdminUserRequest.setGroupId(onboardingAdminGroupResponse.getGroupId());
        createAdminUserRequest.setMerchantId(onboardingThirdStepData.getMerchantId());
        createAdminUserRequest.setCreatedBy(null);
        createAdminUserRequest.setFirstName(onboardingThirdStepData.getFirstName());
        createAdminUserRequest.setLastName(onboardingThirdStepData.getLastName());
        createAdminUserRequest.setKnownAs(onboardingThirdStepData.getKnownAs());
        createAdminUserRequest.setEmail(onboardingThirdStepData.getEmail());
        createAdminUserRequest.setCell(onboardingThirdStepData.getCell());
        createAdminUserRequest.setLandline(onboardingThirdStepData.getLandline());
        createAdminUserRequest.setBirthDate(new java.sql.Date(onboardingThirdStepData.getBirthDate().getTime()));
        createAdminUserRequest.setPosition(onboardingThirdStepData.getPosition());
        createAdminUserRequest.setPrimaryContact(true);
        createAdminUserRequest.setContact(true);
        PlatformCreateAuthUserResponse platformCreateAuthUserResponse = onboardingUserService.createAdminUser(createAdminUserRequest);

        // Save to onboarding entity................................
        MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "No merchant exist for merchant id | " + merchantId));
        MerchantOnboardingEntity merchantOnboardingEntity = merchantOnboardingRepository.findByMerchantId(merchantId).orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "No merchant exist for merchant id | " + merchantId));;
        merchantOnboardingEntity.setAdminContactId(platformCreateAuthUserResponse.getId());
        logger.info("Onboarding Completed for | " + merchantOnboardingEntity.getMerchantId());
        merchantOnboardingRepository.save(merchantOnboardingEntity);
        merchantOnboardingRepository.delete(merchantOnboardingEntity);
        onboardingUserService.updateMerchantToOnboarded(merchantEntity.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/step1/{merchantId}")
    public ResponseEntity<?> putFirstStepData(@RequestBody OnboardingFirstStepData onboardingFirstStepData, @PathVariable long merchantId, HttpServletRequest servletRequest) {

        //create merchant
        PlatformCreateMerchantRequest platformCreateMerchantRequest = new PlatformCreateMerchantRequest();
        platformCreateMerchantRequest.setMerchantName(onboardingFirstStepData.getOnboardingMerchantData().getCompanyName());
        platformCreateMerchantRequest.setTradingAs(onboardingFirstStepData.getOnboardingMerchantData().getTradingAs());
        platformCreateMerchantRequest.setCompanyRegNo(onboardingFirstStepData.getOnboardingMerchantData().getCompanyRegNo());
        platformCreateMerchantRequest.setVatNumber(onboardingFirstStepData.getOnboardingMerchantData().getVatNo());
        platformCreateMerchantRequest.setMerchantClassificationId(onboardingFirstStepData.getOnboardingMerchantData().getMerchantClassificationId());
        platformCreateMerchantRequest.setWebsite(onboardingFirstStepData.getOnboardingMerchantData().getWebsite());
        platformCreateMerchantRequest.setAccountManagerId(onboardingFirstStepData.getOnboardingMerchantData().getAccountManagerId());
        platformCreateMerchantRequest.setSalesPersonId(onboardingFirstStepData.getOnboardingMerchantData().getSalesPersonId());
        platformCreateMerchantRequest.setCreatedBy(getUserFromSession(servletRequest));
        //contains merchant Id to be returned
        PlatformCreateMerchantResponse platformCreateMerchantResponse = merchantService.editMerchant(platformCreateMerchantRequest, merchantId);

        //create physical address
        PlatformCreateAddressRequest platformCreateAddressRequest1 = new PlatformCreateAddressRequest();
        platformCreateAddressRequest1.setAddressLine1(onboardingFirstStepData.getPhysicalAddressData().getAddressLine1());
        platformCreateAddressRequest1.setAddressLine2(onboardingFirstStepData.getPhysicalAddressData().getAddressLine2());
        platformCreateAddressRequest1.setAddressLine3(onboardingFirstStepData.getPhysicalAddressData().getAddressLine3());
        platformCreateAddressRequest1.setAddressLine4(onboardingFirstStepData.getPhysicalAddressData().getAddressLine4());
        platformCreateAddressRequest1.setMerchantId(merchantId);
        platformCreateAddressRequest1.setCityId(onboardingFirstStepData.getPhysicalAddressData().getCityId());
        platformCreateAddressRequest1.setPostalCode(onboardingFirstStepData.getPhysicalAddressData().getPostalCode());
        platformCreateAddressRequest1.setPostalAddress(false);
        PlatformCreateAddressResponse phyiscalAddress = addressService.editAddressOnboarding(platformCreateAddressRequest1, merchantId);

        //create postal address
        PlatformCreateAddressRequest platformCreateAddressRequest2 = new PlatformCreateAddressRequest();
        platformCreateAddressRequest2.setAddressLine1(onboardingFirstStepData.getPostalAddressData().getAddressLine1());
        platformCreateAddressRequest2.setAddressLine2(onboardingFirstStepData.getPostalAddressData().getAddressLine2());
        platformCreateAddressRequest2.setAddressLine3(onboardingFirstStepData.getPostalAddressData().getAddressLine3());
        platformCreateAddressRequest2.setAddressLine4(onboardingFirstStepData.getPostalAddressData().getAddressLine4());
        platformCreateAddressRequest2.setMerchantId(merchantId);
        platformCreateAddressRequest2.setCityId(onboardingFirstStepData.getPostalAddressData().getCityId());
        platformCreateAddressRequest2.setPostalCode(onboardingFirstStepData.getPostalAddressData().getPostalCode());
        platformCreateAddressRequest2.setPostalAddress(true);
        PlatformCreateAddressResponse postalAddress = addressService.editAddressOnboarding(platformCreateAddressRequest2, merchantId);

        // Save to onboarding entity................................
        MerchantOnboardingEntity merchantOnboardingEntity = merchantOnboardingRepository.findByMerchantId(merchantId).orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "No merchant exist for merchant id | " + merchantId));;
        merchantOnboardingEntity.setMerchantId(merchantId);
        merchantOnboardingEntity.setMerchantPhysicalAddressId(phyiscalAddress.getId());
        merchantOnboardingEntity.setMerchantPostalAddressId(postalAddress.getId());
        logger.info("onboarding save |" + merchantOnboardingEntity);
        merchantOnboardingRepository.save(merchantOnboardingEntity);

        return ResponseEntity.ok(platformCreateMerchantResponse);
    }

    @PutMapping("/step2/{merchantId}")
    public ResponseEntity<?> putSecondStepData(@RequestBody OnboardingSecondStepData onboardingSecondStepData, @PathVariable long merchantId) {
        PlatformCreateBillingDetailRequest platformCreateBillingDetailRequest = new PlatformCreateBillingDetailRequest();
        platformCreateBillingDetailRequest.setMerchantId(onboardingSecondStepData.getMerchantId());
        platformCreateBillingDetailRequest.setPaymentMethodId(onboardingSecondStepData.getPaymentTypeId());
        platformCreateBillingDetailRequest.setCreditControllerId(onboardingSecondStepData.getCreditControllerId());
        platformCreateBillingDetailRequest.setRateStructureId(onboardingSecondStepData.getRateStructureId());
        platformCreateBillingDetailRequest.setInvoiceDay(onboardingSecondStepData.getDeductionDay());
        platformCreateBillingDetailRequest.setBankId(onboardingSecondStepData.getBankId());
        platformCreateBillingDetailRequest.setBranchCodeId(onboardingSecondStepData.getBranchCodeId());
        platformCreateBillingDetailRequest.setBankAccountNumber(onboardingSecondStepData.getAccountNo());
        platformCreateBillingDetailRequest.setBankAccountHolder(onboardingSecondStepData.getAccountHolder());
        platformCreateBillingDetailRequest.setAccountTypeId(onboardingSecondStepData.getAccountTypeId());
        platformCreateBillingDetailRequest.setXeroTenantId(onboardingSecondStepData.getXeroTenantId());
        platformCreateBillingDetailRequest.setXeroContactId(onboardingSecondStepData.getXeroContactId());
        if (onboardingSecondStepData.getInvoiceDueDateOffset() != null) {
            platformCreateBillingDetailRequest.setInvoiceDueDateOffset(onboardingSecondStepData.getInvoiceDueDateOffset());
        }
        PlatformCreateBillingDetailResponse platformCreateBillingDetailResponse = billingDetailService.editBillingDetailsOnboarding(platformCreateBillingDetailRequest, merchantId);

        // Save to onboarding entity................................
        // MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "No merchant exist for merchant id | " + merchantId));
        MerchantOnboardingEntity merchantOnboardingEntity = merchantOnboardingRepository.findByMerchantId(merchantId).orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "No merchant exist for merchant id | " + merchantId));;
        merchantOnboardingEntity.setBillingDetailId(platformCreateBillingDetailResponse.getId());

        merchantOnboardingRepository.save(merchantOnboardingEntity);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/step3/{merchantId}")
    public ResponseEntity<?> putThirdStepData(@RequestBody OnboardingThirdStepData onboardingThirdStepData, @PathVariable long merchantId, HttpServletRequest request) {
//        OnboardingAdminGroup onboardingAdminGroup = new OnboardingAdminGroup();
//        onboardingAdminGroup.setMerchantId(onboardingThirdStepData.getMerchantId());
//        onboardingAdminGroup.setCreatedBy(onboardingThirdStepData.getCreatedBy());
//        onboardingAdminGroup.setName("Admin");
//        onboardingAdminGroup.setCode("Admin");
//        onboardingAdminGroup.setDescription("The Admin permission group which contains all permissions.");
//        OnboardingAdminGroupResponse onboardingAdminGroupResponse = onboardingUserService.createAdminGroup(onboardingAdminGroup);
        CreateAdminUserRequest createAdminUserRequest = new CreateAdminUserRequest();
        // createAdminUserRequest.setGroupId(onboardingAdminGroupResponse.getGroupId());
        createAdminUserRequest.setMerchantId(onboardingThirdStepData.getMerchantId());
        createAdminUserRequest.setCreatedBy(null);
        createAdminUserRequest.setFirstName(onboardingThirdStepData.getFirstName());
        createAdminUserRequest.setLastName(onboardingThirdStepData.getLastName());
        createAdminUserRequest.setKnownAs(onboardingThirdStepData.getKnownAs());
        createAdminUserRequest.setEmail(onboardingThirdStepData.getEmail());
        createAdminUserRequest.setCell(onboardingThirdStepData.getCell());
        createAdminUserRequest.setLandline(onboardingThirdStepData.getLandline());
        createAdminUserRequest.setBirthDate(new java.sql.Date(onboardingThirdStepData.getBirthDate().getTime()));
        createAdminUserRequest.setPosition(onboardingThirdStepData.getPosition());
        createAdminUserRequest.setPrimaryContact(true);
        createAdminUserRequest.setContact(true);
        PlatformCreateAuthUserResponse platformCreateAuthUserResponse = onboardingUserService.editAdminUser(createAdminUserRequest, merchantId);

        // Save to onboarding entity................................
        MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "No merchant exist for merchant id | " + merchantId));
        MerchantOnboardingEntity merchantOnboardingEntity = merchantOnboardingRepository.findByMerchantId(merchantId).orElseThrow(() -> new GenericException("No merchant found", HttpStatus.NOT_FOUND, "No merchant exist for merchant id | " + merchantId));;
        merchantOnboardingEntity.setAdminContactId(platformCreateAuthUserResponse.getId());
        logger.info("Onboarding Completed for | " + merchantOnboardingEntity.getMerchantId());
        merchantOnboardingRepository.save(merchantOnboardingEntity);
        merchantOnboardingRepository.delete(merchantOnboardingEntity);
        onboardingUserService.updateMerchantToOnboarded(merchantEntity.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/incomplete/{merchantId}")
    public ResponseEntity<?> getIncopleteOnboarding(@PathVariable long merchantId) {
        return ResponseEntity.ok(onboardingUserService.getIncompleteOnboardingData(merchantId));
    }
}
