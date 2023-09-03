package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.BillingFrequencyEntity;

@Data
@NoArgsConstructor
public class BillingFrequency {
    String name;
    String description;
    String code;

    public BillingFrequency(BillingFrequencyEntity billingFrequencyEntity) {
        this.name = billingFrequencyEntity.getName();
        this.description = billingFrequencyEntity.getDescription();
        this.code = billingFrequencyEntity.getCode();
    }
}
