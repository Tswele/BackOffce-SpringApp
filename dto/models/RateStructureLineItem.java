package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.RateStructureLineItemEntity;

@Data
@NoArgsConstructor
public class RateStructureLineItem {
    String name;
    String description;
    String code;
    TransactionAction transactionAction;
    BillingFrequency billingFrequency;

    public RateStructureLineItem(RateStructureLineItemEntity rateStructureLineItemEntity,
                                 TransactionAction transactionAction) {
        this.name = rateStructureLineItemEntity.getName();
        this.description = rateStructureLineItemEntity.getDescription();
        this.code = rateStructureLineItemEntity.getCode();
        if (transactionAction != null) {
            this.transactionAction = transactionAction;
        }
        this.billingFrequency = new BillingFrequency(rateStructureLineItemEntity.getBillingFrequencyByBillingFrequencyId());
    }

    public RateStructureLineItem(RateStructureLineItemEntity rateStructureLineItemByRateStructureLineItemId) {
        this.setName(rateStructureLineItemByRateStructureLineItemId.getName());
        this.setDescription(rateStructureLineItemByRateStructureLineItemId.getDescription());
        this.setCode(rateStructureLineItemByRateStructureLineItemId.getCode());
        if (rateStructureLineItemByRateStructureLineItemId.getTransactionActionByTransactionActionId() != null) {
            this.setTransactionAction(new TransactionAction(rateStructureLineItemByRateStructureLineItemId.getTransactionActionByTransactionActionId()));
        }
        this.billingFrequency = new BillingFrequency(rateStructureLineItemByRateStructureLineItemId.getBillingFrequencyByBillingFrequencyId());
    }
}

