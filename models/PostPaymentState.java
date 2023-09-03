package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.PaymentStateEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPaymentState {

    private long paymentTypeId;
    private long transactionStateId;

    public PostPaymentState(PaymentStateEntity paymentStateEntity) {
        this.setPaymentTypeId(paymentStateEntity.getPaymentTypeId());
        this.setTransactionStateId(paymentStateEntity.getTransactionStateId());
    }

}
