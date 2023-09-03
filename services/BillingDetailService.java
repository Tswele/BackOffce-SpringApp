package za.co.wirecard.channel.backoffice.services;

import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateBillingDetailRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformGetBillingDetailByMerchantId;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformPutBillingDetail;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateBillingDetailResponse;
import za.co.wirecard.channel.backoffice.models.*;

import java.util.List;

@Service
public interface BillingDetailService {
    List<PaymentType> getPaymentTypes();
    List<Bank> getBanks();
    List<BankBranchCode> getBankBranchCodes(long bankId);
    List<BankAccountType> getAccountTypes();
    List<RateStructure> getRateStructures();
    PlatformGetBillingDetailByMerchantId getBillingDetails(long id);
    PlatformCreateBillingDetailResponse createBillingDetails(PlatformCreateBillingDetailRequest billingDetail);
    void editBillingDetails(long id, PlatformPutBillingDetail billingDetail);
    void deleteBillingDetails(long id);

    PlatformCreateBillingDetailResponse editBillingDetailsOnboarding(PlatformCreateBillingDetailRequest platformCreateBillingDetailRequest, long merchantId);
}
