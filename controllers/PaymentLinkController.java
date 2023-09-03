package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateApplicationPaymentLinkSettingRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetApplicationPaymentLinkSettingResponse;
import za.co.wirecard.channel.backoffice.services.PaymentLinkService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/channel-back-office/api/v1/payment-link")
public class PaymentLinkController {

    private static final Logger logger = LogManager.getLogger(PaymentLinkController.class);
    private final PaymentLinkService paymentLinkService;

    public PaymentLinkController(PaymentLinkService paymentLinkService) {
        this.paymentLinkService = paymentLinkService;
    }

    @PreAuthorize("hasAuthority('CREATE_PAYMENT_LINK')")
    @PostMapping("/setting/create")
    public ResponseEntity<Void> createApplicationPaymentLinkSetting(@Valid @RequestBody CreateApplicationPaymentLinkSettingRequest createApplicationPaymentLinkSettingRequest, HttpServletRequest servletRequest) {
        paymentLinkService.createApplicationPaymentLinkSetting(createApplicationPaymentLinkSettingRequest, servletRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('VIEW_PAYMENT_LINK')")
    @PostMapping("/setting/view/{merchantUid}/{applicationUid}")
    public ResponseEntity<GetApplicationPaymentLinkSettingResponse> viewApplicationPaymentLinkSetting(@PathVariable String merchantUid, @PathVariable String applicationUid, HttpServletRequest servletRequest) {
        return paymentLinkService.viewApplicationPaymentLinkSetting(merchantUid, applicationUid, servletRequest);
    }

}
