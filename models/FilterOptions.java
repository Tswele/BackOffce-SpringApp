package za.co.wirecard.channel.backoffice.models;

import lombok.*;
import za.co.wirecard.channel.backoffice.entities.CardTypeEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterOptions {

    private List<ApplicationType> applicationEntities;
    private List<PaymentInterface> paymentInterfaces;
    private List<PaymentType> paymentTypes;
    private List<TransactionState> transactionStates;
    private List<Gateway> acquiringBanks;
    private List<String> issuingBanks;
    private List<String> ecis;
    private List<String> cardTypes;

}
