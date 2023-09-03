package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateAddressRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformPutAddressesByMerchantId;
import za.co.wirecard.channel.backoffice.services.AddressService;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/clients/address")
public class AddressController {
    private final AddressService addressService;

    private static final Logger logger = LogManager.getLogger(AddressService.class);

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<?> getAddress(@PathVariable long merchantId) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(addressService.getAddress(merchantId)));
        return ResponseEntity.ok(addressService.getAddress(merchantId));
    }

    @PutMapping("/{merchantId}")
    public ResponseEntity<?> editAddress(@RequestBody PlatformPutAddressesByMerchantId address, @PathVariable long merchantId) {
        addressService.editAddress(address, merchantId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    public ResponseEntity<?> createAddress(@RequestBody PlatformCreateAddressRequest address) {
        addressService.createAddress(address);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{merchantId}")
    public ResponseEntity<?> deleteAddress(@PathVariable long merchantId) {
        addressService.deleteAddress(merchantId);
        return ResponseEntity.ok().build();
    }
}
