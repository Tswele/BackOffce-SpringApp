package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.BillingFrequency;
import za.co.wirecard.channel.backoffice.dto.models.RateStructureItemClassification;
import za.co.wirecard.channel.backoffice.dto.models.RateStructureLineItem;
import za.co.wirecard.channel.backoffice.dto.models.requests.*;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetRateStructureLineItemsResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetRateStructureResponse;
import za.co.wirecard.channel.backoffice.services.RateStructureService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/rate-structure")
public class RateStructureController {

    private static final Logger logger = LogManager.getLogger(RateStructureController.class);

    private final RateStructureService rateStructureService;

    public RateStructureController(RateStructureService rateStructureService) {
        this.rateStructureService = rateStructureService;
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAllRateStructures(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "25") int limit) {
        return ResponseEntity.ok(rateStructureService.getAllRateStructures(page, limit));
    }

    @GetMapping("merchant-products/{merchantId}")
    public ResponseEntity<?> getMerchantProductDetails(@PathVariable("merchantId") Long merchantId) {
        return ResponseEntity.ok(rateStructureService.getMerchantProductDetails(merchantId));
    }

    @PutMapping("merchant-products")
    public ResponseEntity<?> setMerchantProductDetails(@RequestBody LinkMerchantProductToPricingModelRequest linkMerchantProductToPricingModelRequest) {
        return ResponseEntity.ok(rateStructureService.setMerchantProductDetails(linkMerchantProductToPricingModelRequest));
    }

    @GetMapping("current-defaults")
    public ResponseEntity<?> getAllCurrentProductDefaults() {
        return ResponseEntity.ok(rateStructureService.getAllCurrentProductDefaults());
    }

    @PutMapping("set-defaults")
    public ResponseEntity<?> setAllCurrentProductDefaults(@RequestBody ProductDefaultSetRequest productDefaultSetRequest) {
        return rateStructureService.setAllCurrentProductDefaults(productDefaultSetRequest);
    }

    @GetMapping("list")
    public ResponseEntity<?> rateStructureDropdown() {
        return ResponseEntity.ok(rateStructureService.rateStructureDropdown());
    }

    @GetMapping("pricing-model-list/{rateStructureId}")
    public ResponseEntity<?> pricingModelDropdown(@PathVariable("rateStructureId") Long rateStructureId) {
        return ResponseEntity.ok(rateStructureService.pricingModelDropdown(rateStructureId));
    }

    @GetMapping("get-historical/{rateStructureId}")
    public ResponseEntity<?> getHistoricalRateStructures(@PathVariable("rateStructureId") Long rateStructureId) {
        return ResponseEntity.ok(rateStructureService.getHistoricalRateStructures(rateStructureId));
    }

    @PostMapping("add")
    public ResponseEntity<?> addRateStructure(@RequestBody CreateRateStructureRequest createRateStructureRequest) {
        return rateStructureService.createRateStructure(createRateStructureRequest);
    }

    @PutMapping("edit")
    public ResponseEntity<?> editRateStructure(@RequestBody EditRateStructureRequest editRateStructureRequest) {
        return rateStructureService.editRateStructure(editRateStructureRequest);
    }

    @PutMapping("approve/{rateStructureId}/{modifiedById}")
    public ResponseEntity<?> approveRateStructure(@PathVariable Long rateStructureId, @PathVariable Long modifiedById) {
        return rateStructureService.approveRateStructure(rateStructureId, modifiedById);
    }

    @GetMapping("pricing-model/{pricingModelId}")
    public ResponseEntity<?> getPricingModelById(@PathVariable Long pricingModelId) {
        return ResponseEntity.ok(rateStructureService.getPricingModelById(pricingModelId));
    }

    @PostMapping("add-global-pricing-model/{rateStructureId}")
    public ResponseEntity<?> addPricingModel(@PathVariable Long rateStructureId, @RequestBody CreatePricingModelRequest createPricingModelRequest) {
        return rateStructureService.createPricingModel(rateStructureId, createPricingModelRequest);
    }

    @PutMapping("edit-global-pricing-model/{pricingModelId}")
    public ResponseEntity<?> editGlobalPricingModel(@PathVariable Long pricingModelId, @RequestBody EditPricingModelRequest editPricingModelRequest) {
        return rateStructureService.editGlobalPricingModel(pricingModelId, editPricingModelRequest);
    }

    @GetMapping("billing-frequency")
    public ResponseEntity<List<BillingFrequency>> getBillingFrequencies() {
        return ResponseEntity.ok(rateStructureService.getBillingFrequencies());
    }

    @GetMapping("line-items")
    public ResponseEntity<GetRateStructureLineItemsResponse> getRateStructureLineItems() {
        return ResponseEntity.ok(rateStructureService.getRateStructureLineItems());
    }

    @GetMapping("line-items/{rateStructureId}")
    public ResponseEntity<GetRateStructureLineItemsResponse> getRateStructureLineItems(@PathVariable long rateStructureId) {
        return ResponseEntity.ok(rateStructureService.getRateStructureLineItemsByRateStructureId(rateStructureId));
    }

    @GetMapping("{rateStructureId}/{rateStructureVersion}")
    public ResponseEntity<GetRateStructureResponse> getRateStructureById(@PathVariable long rateStructureId, @PathVariable long rateStructureVersion) {
        return ResponseEntity.ok(rateStructureService.getRateStructureById(rateStructureId, rateStructureVersion));
    }
}
