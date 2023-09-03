package za.co.wirecard.channel.backoffice.services;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.dto.models.GetTotalsInHistoryResponse;
import za.co.wirecard.channel.backoffice.dto.models.requests.SetTransactionState;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetCardTransactionResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetStateTotalsInHistoryResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetTransactionLegDetailResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetTransactionLegResponse;
import za.co.wirecard.channel.backoffice.entities.InterfaceEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;
import za.co.wirecard.channel.backoffice.models.*;
import za.co.wirecard.common.exceptions.NotFoundException;

import java.util.Date;
import java.util.List;

public interface TransactionService {

    Page<Transaction> findAllTransactions(int page, int limit, Specification specification);
    FilterOptions findFilterOptions(List<Long> merchantId, List<Long> applicationIds);
    GetTotalsInHistoryResponse calculateTotals(Long merchantId,
                                               Long applicationId,
                                               Long paymentTypeId,
                                               Long transactionStateId,
                                               Date startDate,
                                               Date endDate,
                                               String stringCriteria,
                                               String stringSearch);
    List<GetStateTotalsInHistoryResponse> calculateStateTotalsByMerchantId(Long merchantId, Date startDate);
    List<MerchantEntity> getMerchants();
    List<Long> getMerchantIds(List<String> merchantUids);
    List<Long> getPaymentTypeIds(List<String> paymentTypeCodes);
    List<Long> getTransactionStateIds(List<String> transactionStateCodes);
    List<GetTransactionLegResponse> getTransactionLegs(long transactionId);
    List<InterfaceEntity> getInterfacesByApplicationId(List<String> applicationUids) throws NotFoundException;
    List<Long> getCardTransactionByCardBinAndLastFour(String cardBin, String lastFour, Date startDate, Date endDate) throws NotFoundException;
    GetCardTransactionResponse getCardTransactionByAuthorisationId(String authorisationId, Date startDate, Date endDate) throws NotFoundException;
    List<GetTransactionLegDetailResponse> getTransactionLegDetail(long transactionLegId);
    List<TransactionState> getAllTransactionStates();
    List<PaymentState> getPaymentStatesByPaymentTypeId(long paymentTypeId);
    void setTransactionState(SetTransactionState setTransactionState);

    void postPaymentState(long paymentTypeId, List<PostPaymentState> postPaymentState);

}
