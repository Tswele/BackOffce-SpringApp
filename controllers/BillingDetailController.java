package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.OnboardingSecondStepData;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateBillingDetailRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformPutBillingDetail;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateBillingDetailResponse;
import za.co.wirecard.channel.backoffice.services.BillingDetailService;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/clients/billing-details")

public class BillingDetailController {

    private static final Logger logger = LogManager.getLogger(BillingDetailService.class);

    private final BillingDetailService billingDetailService;

    public BillingDetailController(BillingDetailService billingDetailService) {
        this.billingDetailService = billingDetailService;
    }

    @GetMapping("/payment-types")
    public ResponseEntity<?> getPaymentTypes() {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(billingDetailService.getPaymentTypes()));
        return ResponseEntity.ok(billingDetailService.getPaymentTypes());
    }

    @GetMapping("/banks")
    public ResponseEntity<?> getBanks() {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(billingDetailService.getBanks()));
        return ResponseEntity.ok(billingDetailService.getBanks());
    }

    @GetMapping("/bank-branch-codes")
    public ResponseEntity<?> getBankBranchCode(long bankId) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(billingDetailService.getBankBranchCodes(bankId)));
        return ResponseEntity.ok(billingDetailService.getBankBranchCodes(bankId));
    }

    @GetMapping("/account-types")
    public ResponseEntity<?> getAccountTypes(HttpServletRequest servletRequest) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(billingDetailService.getAccountTypes()));
        return ResponseEntity.ok(billingDetailService.getAccountTypes());
    }

    @GetMapping("/rate-structures")
    public ResponseEntity<?> getRateStructures() {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(billingDetailService.getAccountTypes()));
        return ResponseEntity.ok(billingDetailService.getRateStructures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBillingDetails(@PathVariable long id){
        return ResponseEntity.ok(billingDetailService.getBillingDetails(id));
    }

    @PostMapping("")
    public ResponseEntity<?> createBillingDetails(@RequestBody PlatformCreateBillingDetailRequest billingDetails) {
        billingDetailService.createBillingDetails(billingDetails);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editBillingDetails(@PathVariable long id, @RequestBody OnboardingSecondStepData onboardingSecondStepData) {
        logger.info("BILLING DETAIL | " + onboardingSecondStepData.toString());
        PlatformCreateBillingDetailRequest platformCreateBillingDetailRequest = new PlatformCreateBillingDetailRequest(onboardingSecondStepData, id);
        billingDetailService.editBillingDetailsOnboarding(platformCreateBillingDetailRequest, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBillingDetails(@PathVariable long id) {
        billingDetailService.deleteBillingDetails(id);
        return ResponseEntity.ok().build();
    }
}
