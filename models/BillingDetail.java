package za.co.wirecard.channel.backoffice.models;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BillingDetail {
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
}
