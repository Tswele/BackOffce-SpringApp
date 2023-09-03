package za.co.wirecard.channel.backoffice.dto.models.responses;

import za.co.wirecard.channel.backoffice.models.PaymentInterface;
import za.co.wirecard.channel.backoffice.models.PaymentType;
import za.co.wirecard.channel.backoffice.models.TransactionState;

import java.util.List;

public class PlatformGetFilterOptionsByMerchantIdResponse {
    private List<PaymentInterface> paymentInterfaces;
    private List<PaymentType> paymentType;
    private List<TransactionState> transactionStates;
}