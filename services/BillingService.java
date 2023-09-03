package za.co.wirecard.channel.backoffice.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateBillingRunRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetBillingRunHistoryResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetInvoiceDetailsResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetInvoiceHistoryResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetLineItemHistoryResponse;
import za.co.wirecard.channel.backoffice.models.Transaction;

import java.util.List;

@Service
public interface BillingService {
    Page<GetBillingRunHistoryResponse> viewBillingRunHistory(int page, int limit, String stringCriteria, String stringSearch);
    ResponseEntity<?> setBillingRunApprovedBy(Long billingRunId, Long approvedById);
    ResponseEntity<?> createBillingRun(CreateBillingRunRequest createBillingRunRequest);
    Page<GetInvoiceHistoryResponse> viewInvoiceHistory(int page, int limit, Long billingRunId, String stringCriteria, String stringSearch);
    List<GetInvoiceDetailsResponse> getInvoiceDetails(Long invoiceId);
    Page<GetLineItemHistoryResponse> viewLineItemHistory(int page, int limit, Long invoiceId, String stringCriteria, String stringSearch);
    Page<Transaction> viewTransactionHistory(int page, int limit, Long id, Boolean isLineItem, String stringCriteria, String stringSearch);
}
