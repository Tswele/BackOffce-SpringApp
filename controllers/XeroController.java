package za.co.wirecard.channel.backoffice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.responses.XeroAuthorizationResponse;
import za.co.wirecard.channel.backoffice.services.BillingService;
import za.co.wirecard.channel.backoffice.services.XeroService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/xero")
public class XeroController {

    private final XeroService xeroService;
    private final BillingService billingService;

    public XeroController(XeroService xeroService,
                          BillingService billingService) {
        this.xeroService = xeroService;
        this.billingService = billingService;
    }

    //    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'APPROVE_BILLING_RUN')")
    @GetMapping("/authorization")
    public ResponseEntity<XeroAuthorizationResponse> xeroAuthorization(@RequestParam() Long billingRunId, @RequestParam() Long approvedById) throws IOException {
        billingService.setBillingRunApprovedBy(billingRunId, approvedById);
        XeroAuthorizationResponse response = xeroService.xeroAuthorization();
        return ResponseEntity.ok(response);
    }
}
