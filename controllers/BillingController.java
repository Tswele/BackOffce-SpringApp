package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateBillingRunRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetBillingRunHistoryResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetInvoiceDetailsResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetInvoiceHistoryResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetLineItemHistoryResponse;
import za.co.wirecard.channel.backoffice.models.Transaction;
import za.co.wirecard.channel.backoffice.services.BillingService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/billing")
public class BillingController {

    private static final Logger logger = LogManager.getLogger(BillingController.class);

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/billing-run/history")
    public Page<GetBillingRunHistoryResponse> viewBillingRunHistory(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "25") int limit,
                                                                    //@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                                    //@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                                    @RequestParam(required = false) String stringCriteria,
                                                                    @RequestParam(required = false) String stringSearch)
    {
        return billingService.viewBillingRunHistory(page, limit, stringCriteria, stringSearch);
    }

    @PostMapping("/billing-run/start")
    public ResponseEntity<?> createBillingRun(@RequestBody CreateBillingRunRequest createBillingRunRequest) {
        return billingService.createBillingRun(createBillingRunRequest);
    }

    @GetMapping("/invoice/history")
    public Page<GetInvoiceHistoryResponse> viewInvoiceHistory(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "25") int limit,
                                                              @RequestParam() Long billingRunId,
                                                              @RequestParam(required = false) String stringCriteria,
                                                              @RequestParam(required = false) String stringSearch)
    {
        return billingService.viewInvoiceHistory(page, limit, billingRunId, stringCriteria, stringSearch);
    }

    @GetMapping("/invoice/{invoiceId}")
    public List<GetInvoiceDetailsResponse> getInvoiceDetails(@PathVariable Long invoiceId) {
        return billingService.getInvoiceDetails(invoiceId);
    }

    @GetMapping("/line-item/history")
    public Page<GetLineItemHistoryResponse> viewLineItemHistory(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "25") int limit,
                                                                @RequestParam() Long invoiceId,
                                                                @RequestParam(required = false) String stringCriteria,
                                                                @RequestParam(required = false) String stringSearch)
    {
        return billingService.viewLineItemHistory(page, limit, invoiceId, stringCriteria, stringSearch);
    }

    @GetMapping("/transaction/history")
    public Page<Transaction> viewTransactionHistory(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "25") int limit,
                                                    @RequestParam() Long id,
                                                    @RequestParam() Boolean isLineItem,
                                                    @RequestParam(required = false) String stringCriteria,
                                                    @RequestParam(required = false) String stringSearch)
    {
        return billingService.viewTransactionHistory(page, limit, id, isLineItem, stringCriteria, stringSearch);
    }

}
