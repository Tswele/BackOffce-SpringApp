package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.BillingRunStatusEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingRunStatus {
    private Long id;
    private String name;
    private String code;
    private String description;

    public BillingRunStatus(BillingRunStatusEntity billingRunStatusByBillingRunStatusId) {
        this.setId(billingRunStatusByBillingRunStatusId.getId());
        this.setName(billingRunStatusByBillingRunStatusId.getName());
        this.setCode(billingRunStatusByBillingRunStatusId.getCode());
        this.setDescription(billingRunStatusByBillingRunStatusId.getDescription());
    }
}
