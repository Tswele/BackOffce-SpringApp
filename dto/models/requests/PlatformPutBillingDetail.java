package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.models.BackOfficeUser;
import za.co.wirecard.channel.backoffice.models.PutPaymentType;
import za.co.wirecard.channel.backoffice.models.RateStructure;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  PlatformPutBillingDetail {
    @NotNull
    private long merchantId;
    @NotNull
    private PutPaymentType paymentMethod;
    private BackOfficeUser creditController;
    @NotNull
    private RateStructure rateStructure;
    private int invoiceDay;
//    //private Bank bank;//private long bankId;
//    private long branchCodeId;
//    private String bankAccountNumber;
//    private String bankAccountHolder;

}
