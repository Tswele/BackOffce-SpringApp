package za.co.wirecard.channel.backoffice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateShopifyConfigRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetShopifyConfigResponse;
import za.co.wirecard.channel.backoffice.services.ShopifyService;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/shopify")
public class ShopifyController {

    private final ShopifyService shopifyService;

    public ShopifyController(ShopifyService shopifyService) {
        this.shopifyService = shopifyService;
    }

    @GetMapping("/configuration/{applicationUid}")
    public ResponseEntity<GetShopifyConfigResponse> getShopifyConfiguration(@PathVariable String applicationUid) {
        return ResponseEntity.ok(shopifyService.getShopifyConfiguration(applicationUid));
    }

    @PostMapping("/configuration")
    public ResponseEntity<?> createShopifyConfiguration(@RequestBody CreateShopifyConfigRequest createShopifyConfigRequest) {
        return shopifyService.createShopifyConfiguration(createShopifyConfigRequest);
    }

    @PutMapping("/configuration")
    public ResponseEntity<?> editShopifyConfiguration(@RequestBody CreateShopifyConfigRequest createShopifyConfigRequest) {
        return shopifyService.editShopifyConfiguration(createShopifyConfigRequest);
    }
}
