package za.co.wirecard.channel.backoffice.dto.models;


import lombok.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformGetBillingDetailByMerchantId;
import za.co.wirecard.channel.backoffice.entities.BillingDetailEntity;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OnboardingSecondStepData {
    private String accountHolder;
    private long paymentTypeId;
    private long bankId;
    private long branchCodeId;
    private String accountNo;
    private long accountTypeId;
    private int deductionDay;
    private long merchantId;
    private long creditControllerId;
    private long rateStructureId;
    private String xeroTenantId;
    private String xeroContactId;
    private Long invoiceDueDateOffset;

    public OnboardingSecondStepData(BillingDetailEntity billingDetailEntity) {
        this.paymentTypeId = billingDetailEntity.getPaymentTypeEntity().getId();
        this.deductionDay = billingDetailEntity.getInvoiceDay();
        this.merchantId = billingDetailEntity.getMerchantId();
        this.creditControllerId = billingDetailEntity.getCreditController().getId();
        this.rateStructureId = billingDetailEntity.getRateStructure().getId();
        this.bankId = billingDetailEntity.getBankEntity().getId();
        this.accountHolder = billingDetailEntity.getAccountHolder();
        this.accountNo = billingDetailEntity.getAccountNumber();
        this.accountTypeId = billingDetailEntity.getBankAccountTypeEntity().getId();
        this.branchCodeId = billingDetailEntity.getBankBranchCodeEntity().getId();
        this.xeroTenantId = billingDetailEntity.getXeroTenantId();
        this.xeroContactId = billingDetailEntity.getXeroContactId();
        if (billingDetailEntity.getInvoiceDueDateOffset() != null) {
            this.invoiceDueDateOffset = billingDetailEntity.getInvoiceDueDateOffset();
        }
    }

    public OnboardingSecondStepData(PlatformGetBillingDetailByMerchantId platformGetBillingDetailByMerchantId) {
        this.paymentTypeId = platformGetBillingDetailByMerchantId.getPaymentMethod().getId();
        this.deductionDay = platformGetBillingDetailByMerchantId.getInvoiceDay();
        this.creditControllerId = platformGetBillingDetailByMerchantId.getCreditController().getId();
        this.rateStructureId = platformGetBillingDetailByMerchantId.getRateStructure().getId();
        this.bankId = platformGetBillingDetailByMerchantId.getBranchCodeId();
        this.accountHolder = platformGetBillingDetailByMerchantId.getBankAccountHolder();
        this.accountNo = platformGetBillingDetailByMerchantId.getBankAccountNumber();
        this.accountTypeId = platformGetBillingDetailByMerchantId.getAccountTypeId();
        this.branchCodeId = platformGetBillingDetailByMerchantId.getBranchCodeId();
        this.xeroTenantId = platformGetBillingDetailByMerchantId.getXeroTenantId();
        this.xeroContactId = platformGetBillingDetailByMerchantId.getXeroContactId();
        if (platformGetBillingDetailByMerchantId.getInvoiceDueDateOffset() != null) {
            this.invoiceDueDateOffset = platformGetBillingDetailByMerchantId.getInvoiceDueDateOffset();
        }
    }
}
