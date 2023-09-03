package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.services.PricingModelService;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/pricing-model")
public class PricingModelController {

    private static final Logger logger = LogManager.getLogger(PricingModelController.class);

    private final PricingModelService pricingModelService;

    public PricingModelController(PricingModelService pricingModelService ) {
        this.pricingModelService = pricingModelService;
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAllGlobalPricingModels(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "25") int limit) {
        return ResponseEntity.ok(pricingModelService.getAllGlobalPricingModels(page, limit));
    }
}
