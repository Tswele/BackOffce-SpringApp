package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import za.co.wirecard.channel.backoffice.dto.models.OnboardingSecondStepData;
import za.co.wirecard.channel.backoffice.models.BillingDetail;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlatformCreateBillingDetailRequest {
    long merchantId;
    @NotNull
    private long paymentMethodId;
    private long creditControllerId;
    @NotNull
    private long rateStructureId;
    private int invoiceDay;
    //private Bank bank;
    private long bankId;
    private long branchCodeId;
    private String bankAccountNumber;
    private String bankAccountHolder;
    //private BankAccountType bankAccountType;
    private long accountTypeId;
    private String xeroTenantId;
    private String xeroContactId;
    private Long invoiceDueDateOffset;

    public PlatformCreateBillingDetailRequest(OnboardingSecondStepData onboardingSecondStepData, long id) {
        this.merchantId = id;
        this.paymentMethodId = onboardingSecondStepData.getPaymentTypeId();
        this.creditControllerId = onboardingSecondStepData.getCreditControllerId();
        this.rateStructureId = onboardingSecondStepData.getRateStructureId();
        this.invoiceDay = onboardingSecondStepData.getDeductionDay();
        this.bankId = onboardingSecondStepData.getBankId();
        this.branchCodeId = onboardingSecondStepData.getBranchCodeId();
        this.bankAccountNumber = onboardingSecondStepData.getAccountNo();
        this.bankAccountHolder = onboardingSecondStepData.getAccountHolder();
        this.accountTypeId = onboardingSecondStepData.getAccountTypeId();
        this.xeroTenantId = onboardingSecondStepData.getXeroTenantId();
        this.xeroContactId = onboardingSecondStepData.getXeroContactId();
        if (onboardingSecondStepData.getInvoiceDueDateOffset() != null) {
            this.invoiceDueDateOffset = onboardingSecondStepData.getInvoiceDueDateOffset();
        }
    }
}
