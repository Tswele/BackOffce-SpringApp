package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.MerchantProductData;
import za.co.wirecard.channel.backoffice.dto.models.requests.SetApplicationProductRequest;
import za.co.wirecard.channel.backoffice.services.ProductService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/products")
public class ProductController {

    private final ProductService productService;

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<?> getMerchantProducts(@PathVariable("merchantId") long merchantId){
        return productService.getMerchantProducts(merchantId);
    }

    @PostMapping("/{merchantId}")
    public ResponseEntity<?> setMerchantProducts(@PathVariable("merchantId") long merchantId, @RequestBody MerchantProductData merchantProductData){
        return productService.setMerchantProducts(merchantId, merchantProductData);
    }

    @GetMapping("application/{applicationId}")
    public ResponseEntity<?> getApplicationProducts(@PathVariable("applicationId") long applicationId){
        return productService.getApplicationProducts(applicationId);
    }

    @PostMapping("application/{applicationId}/{backOfficeUserId}")
    public ResponseEntity<?> setApplicationProducts(@PathVariable("applicationId") long applicationId, @PathVariable("backOfficeUserId") long backOfficeUserId, @RequestBody List<SetApplicationProductRequest> setApplicationProductRequestList){
        return productService.setApplicationProducts(applicationId, backOfficeUserId, setApplicationProductRequestList);
    }

    @GetMapping("status")
    public ResponseEntity<?> getMerchantProductStatus(@RequestParam long merchantId, @RequestParam long productId) {
        return ResponseEntity.ok(productService.getMerchantProductStatus(merchantId, productId));
    }

}
