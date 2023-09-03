package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.models.BackOfficeUser;
import za.co.wirecard.channel.backoffice.models.RateStructure;
import za.co.wirecard.channel.backoffice.models.StaticPaymentType;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformGetBillingDetailByMerchantId {

    @NotNull
    private long id;
    @NotNull
    private StaticPaymentType paymentMethod;
    private BackOfficeUser creditController;
    @NotNull
    private RateStructure rateStructure;
    private Integer invoiceDay;
    private long bankId;
    private long branchCodeId;
    private String bankAccountNumber;
    private String bankAccountHolder;
    private long accountTypeId;
    private String xeroTenantId;
    private String xeroContactId;
    private Long invoiceDueDateOffset;

}
