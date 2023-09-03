package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.TransactionActionEntity;

@Data
@NoArgsConstructor
public class TransactionAction {
    String name;
    String description;
    String code;
    Long paymentTypeId;

    public TransactionAction(TransactionActionEntity transactionActionEntity) {
        this.name = transactionActionEntity.getName();
        this.description = transactionActionEntity.getDescription();
        this.code = transactionActionEntity.getCode();
        this.paymentTypeId = transactionActionEntity.getPaymentTypeByPaymentTypeId().getId();
    }
}
