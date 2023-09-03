package za.co.wirecard.channel.backoffice.dto.models;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {
    @NotNull
    private String payerClientCode;
    @NotNull
    private String payerTrasactionID;
    @NotNull
    private double amountPaid;
    @NotNull
    private String MSISDN;
    @NotNull
    private String cpgTransactionID;
    @NotNull
    private String currencyCode;
    @NotNull
    private String paymentDate;
}
