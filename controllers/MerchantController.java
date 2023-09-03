package za.co.wirecard.channel.backoffice.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import za.co.wirecard.channel.backoffice.config.ApplicationConstants;
import za.co.wirecard.channel.backoffice.dto.models.OnboardingFirstStepData;
import za.co.wirecard.channel.backoffice.dto.models.OnboardingThirdStepData;
import za.co.wirecard.channel.backoffice.dto.models.requests.MerchantDocument;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateAddressRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateMerchantRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformGetMerchantsResponse;
import za.co.wirecard.channel.backoffice.dto.models.s3.MultipartFileUnique;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.requests.CreateAdminUserRequest;
import za.co.wirecard.channel.backoffice.services.AddressService;
import za.co.wirecard.channel.backoffice.services.MerchantService;
import za.co.wirecard.channel.backoffice.services.OnboardingUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/clients")
public class MerchantController {

    private final MerchantService merchantService;
    private final OnboardingUserService onboardingUserService;
    private final AddressService addressService;

    private static final Logger logger = LogManager.getLogger(MerchantService.class);

    public MerchantController(MerchantService merchantService, OnboardingUserService onboardingUserService, AddressService addressService) {
        this.merchantService = merchantService;
        this.onboardingUserService = onboardingUserService;
        this.addressService = addressService;
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getMerchantsWithFilters(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "25") int limit,
                                                     @RequestParam(required = false) String stringCriteria,
                                                     @RequestParam(required = false) String stringSearch) {
        return ResponseEntity.ok(merchantService.getMerchantsByFilter(page, limit, stringCriteria, stringSearch));
    }

    @GetMapping("/onboarding")
    public ResponseEntity<?> getOnboardingMerchants(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "25") int limit,
                                                    @RequestParam(required = false) String stringCriteria,
                                                    @RequestParam(required = false) String stringSearch) {
        return ResponseEntity.ok(merchantService.getOnboardingMerchantsByFilter(page, limit, stringCriteria, stringSearch));
    }

    @GetMapping("")
    public ResponseEntity<?> getMerchants(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int limit, @RequestParam(required = false) String searchString) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(merchantService.getMerchants(page, limit, searchString)));
        return ResponseEntity.ok(merchantService.getMerchants(page, limit, searchString));
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'MERCHANT_VIEW')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllMerchants(HttpServletRequest httpServletRequest) {
        List<PlatformGetMerchantsResponse> merchants = merchantService.getAllMerchants();
        return ResponseEntity.ok(merchants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMerchant(@PathVariable long id) {
        return ResponseEntity.ok(merchantService.getMerchant(id));
    }

    @PostMapping("")
    public ResponseEntity<?> createMerchant(@RequestBody PlatformCreateMerchantRequest merchant) {
        return ResponseEntity.ok(merchantService.createMerchant(merchant));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable long id, @RequestBody OnboardingFirstStepData onboardingFirstStepData, HttpServletRequest servletRequest) {
        //create merchant
        logger.info("UPDATE_MERCHANT | " + onboardingFirstStepData.toString());
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
        merchantService.editMerchant(platformCreateMerchantRequest, id);

        //create physical address
        PlatformCreateAddressRequest platformCreateAddressRequest1 = new PlatformCreateAddressRequest();
        platformCreateAddressRequest1.setAddressLine1(onboardingFirstStepData.getPhysicalAddressData().getAddressLine1());
        platformCreateAddressRequest1.setAddressLine2(onboardingFirstStepData.getPhysicalAddressData().getAddressLine2());
        platformCreateAddressRequest1.setAddressLine3(onboardingFirstStepData.getPhysicalAddressData().getAddressLine3());
        platformCreateAddressRequest1.setAddressLine4(onboardingFirstStepData.getPhysicalAddressData().getAddressLine4());
        platformCreateAddressRequest1.setMerchantId(id);
        platformCreateAddressRequest1.setCityId(onboardingFirstStepData.getPhysicalAddressData().getCityId());
        platformCreateAddressRequest1.setPostalCode(onboardingFirstStepData.getPhysicalAddressData().getPostalCode());
        platformCreateAddressRequest1.setPostalAddress(false);
        addressService.editAddressOnboarding(platformCreateAddressRequest1, id);

        //create postal address
        PlatformCreateAddressRequest platformCreateAddressRequest2 = new PlatformCreateAddressRequest();
        platformCreateAddressRequest2.setAddressLine1(onboardingFirstStepData.getPostalAddressData().getAddressLine1());
        platformCreateAddressRequest2.setAddressLine2(onboardingFirstStepData.getPostalAddressData().getAddressLine2());
        platformCreateAddressRequest2.setAddressLine3(onboardingFirstStepData.getPostalAddressData().getAddressLine3());
        platformCreateAddressRequest2.setAddressLine4(onboardingFirstStepData.getPostalAddressData().getAddressLine4());
        platformCreateAddressRequest2.setMerchantId(id);
        platformCreateAddressRequest2.setCityId(onboardingFirstStepData.getPostalAddressData().getCityId());
        platformCreateAddressRequest2.setPostalCode(onboardingFirstStepData.getPostalAddressData().getPostalCode());
        platformCreateAddressRequest2.setPostalAddress(true);
        addressService.editAddressOnboarding(platformCreateAddressRequest2, id);

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/merchant-types")
    public ResponseEntity<?> getMerchantTypes(){
        return ResponseEntity.ok(merchantService.getMerchantTypes());
    }
    @GetMapping("/merchant-statuses")
    public ResponseEntity<?> getMerchantStatuses(){
        return ResponseEntity.ok(merchantService.getMerchantStatuses());
    }
    @GetMapping("/account-managers")
    public ResponseEntity<?> getAccountManagers(){
        return ResponseEntity.ok(merchantService.getAccountManagers());
    }
    @GetMapping("/sales-persons")
    public ResponseEntity<?> getSalesPersons(){
        return ResponseEntity.ok(merchantService.getSalesPersons());
    }
    @GetMapping("/credit-controllers")
    public ResponseEntity<?> getCreditControllers(){
        return ResponseEntity.ok(merchantService.getCreditControllers());
    }

    @GetMapping("/merchant-view-detail/{merchantId}")
    public ResponseEntity<?> getMerchantViewDetail(@PathVariable long merchantId){
        return ResponseEntity.ok(merchantService.getMerchantViewDetail(merchantId));
    }

    @PutMapping("/primary-contact/{id}")
    public ResponseEntity<?> updateMerchantPrimaryContact(@PathVariable long id, @RequestBody OnboardingThirdStepData onboardingThirdStepData) {
        CreateAdminUserRequest createAdminUserRequest = new CreateAdminUserRequest();
        createAdminUserRequest.setMerchantId(onboardingThirdStepData.getMerchantId());
        createAdminUserRequest.setCreatedBy(null);
        createAdminUserRequest.setFirstName(onboardingThirdStepData.getFirstName());
        createAdminUserRequest.setLastName(onboardingThirdStepData.getLastName());
        createAdminUserRequest.setKnownAs(onboardingThirdStepData.getKnownAs());
        createAdminUserRequest.setEmail(onboardingThirdStepData.getEmail());
        createAdminUserRequest.setCell(onboardingThirdStepData.getCell());
        createAdminUserRequest.setLandline(onboardingThirdStepData.getLandline());
        createAdminUserRequest.setBirthDate(new java.sql.Date(onboardingThirdStepData.getBirthDate().getTime()));
        createAdminUserRequest.setPosition(onboardingThirdStepData.getPosition());;
        onboardingUserService.editAdminUser(createAdminUserRequest, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "documents/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> postMerchantDocuments(@PathVariable long id, @ModelAttribute MultipartFileUnique multipartFileUnique) {
        if (multipartFileUnique.getImage() == null) {
            throw new GenericException(ApplicationConstants.S3_BUCKET_IMAGE_OBJECT_ERROR.value(), HttpStatus.BAD_REQUEST, "Multipart form data was not sent correctly | " + multipartFileUnique.toString());
        }
        return ResponseEntity.ok(merchantService.postMerchantDocuments(multipartFileUnique, id));
    }

    @DeleteMapping(value = "documents/{id}/{merchantId}")
    public ResponseEntity<?> deleteMerchantDocuments(@PathVariable long id, @PathVariable long merchantId) {
        return ResponseEntity.ok(merchantService.deleteMerchantDocuments(id, merchantId));
    }

    @GetMapping(value = "documents/view/{id}")
    public ResponseEntity<?> getMerchantDocuments(@PathVariable long id) {
        return ResponseEntity.ok(merchantService.getMerchantDocuments(id));
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


}
