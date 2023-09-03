package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateInterfaceRequest;
import za.co.wirecard.channel.backoffice.models.Gateway;
import za.co.wirecard.channel.backoffice.services.InterfaceService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/channel-back-office/api/v1/clients/application/interface")
public class InterfaceController {
    private final InterfaceService interfaceService;

    private static final Logger logger = LogManager.getLogger(InterfaceService.class);

    public InterfaceController(InterfaceService interfaceService) {
        this.interfaceService = interfaceService;
    }

    @GetMapping("")
    public ResponseEntity<?> getInterfaces(HttpServletRequest servletRequest) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(interfaceService.getInterfaces(servletRequest)));
        return ResponseEntity.ok(interfaceService.getInterfaces(servletRequest));
    }

    @GetMapping("/{interfaceId}")
    public ResponseEntity<?> getInterfaceByInterfaceId(@PathVariable(name = "interfaceId") long interfaceId) {
        return ResponseEntity.ok(interfaceService.getInterfaceByInterfaceId(interfaceId));
    }

    @PutMapping("/{interfaceId}")
    public ResponseEntity<?> editInterfaceByInterfaceId(@PathVariable(name = "interfaceId") long interfaceId, @RequestBody @Valid PlatformCreateInterfaceRequest platformCreateInterfaceRequest) {
        interfaceService.editInterfaceByInterfaceId(interfaceId, platformCreateInterfaceRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{page}/{limit}/{applicationId}")
    public ResponseEntity<?> getInterfacesByApplicationId(@PathVariable int page, @PathVariable int limit, @PathVariable long applicationId, HttpServletRequest servletRequest) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(interfaceService.getInterfacesByApplicationId(page, limit ,applicationId, servletRequest)));
        return ResponseEntity.ok(interfaceService.getInterfacesByApplicationId(page, limit,applicationId, servletRequest));
    }
//    @GetMapping("/{page}/{limit}/{merchantId}")
//    public ResponseEntity<?> getInterfacesByMerchantId(@PathVariable int page, @PathVariable int limit, @PathVariable long merchantId, HttpServletRequest servletRequest) {
//        logger.info("This is the Response Entity: " + ResponseEntity.ok(interfaceService.getInterfacesByMerchantId(page, limit ,merchantId, servletRequest)));
//        return ResponseEntity.ok(interfaceService.getInterfacesByMerchantId(page, limit,merchantId, servletRequest));
//    }
    @PostMapping("")
        public ResponseEntity<?> createInterface(@RequestBody PlatformCreateInterfaceRequest interfaceRequest, HttpServletRequest servletRequest) {
        interfaceService.createInterface(interfaceRequest, servletRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/security-methods")
    public ResponseEntity<?> getSecurityMethods(HttpServletRequest servletRequest) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(interfaceService.getSecurityMethods(servletRequest)));
        return ResponseEntity.ok(interfaceService.getSecurityMethods(servletRequest));
    }

    @GetMapping("/trading-currencies")
    public ResponseEntity<?> getTradingCurrencies(HttpServletRequest servletRequest) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(interfaceService.getTradingCurrencies(servletRequest)));
        return ResponseEntity.ok(interfaceService.getTradingCurrencies(servletRequest));
    }

    @GetMapping("/currencies")
    public ResponseEntity<?> getCurrencies(HttpServletRequest servletRequest) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(interfaceService.getCurrencies(servletRequest)));
        return ResponseEntity.ok(interfaceService.getCurrencies(servletRequest));
    }

    @GetMapping("/gateways")
    public ResponseEntity<?> getGateways(HttpServletRequest servletRequest) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(interfaceService.getGateways(servletRequest)));
        List<Gateway> response = interfaceService.getGateways(servletRequest);
        for (Gateway gateway: response) {
            logger.info("GATEWAY_ID " + gateway.getId());
            logger.info("GATEWAY_NAME " + gateway.getName());
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/tds-merchant-types")
    public ResponseEntity<?> getTdsMerchantTypes(HttpServletRequest servletRequest) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(interfaceService.getTdsMerchantTypes(servletRequest)));
        return ResponseEntity.ok(interfaceService.getTdsMerchantTypes(servletRequest));
    }
}


