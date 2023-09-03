package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.LineItemEntity;
import za.co.wirecard.channel.backoffice.entities.SubLineItemEntity;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubLineItem {
    private Long id;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private Boolean hasTransactions;
    private Boolean isLineItem;

    public SubLineItem(LineItemEntity lineItemEntity) {
        this.setId(lineItemEntity.getId());
        this.setName("");
        this.setDescription(lineItemEntity.getDescription());
        this.setUnitPrice(lineItemEntity.getUnitPrice());
        this.setHasTransactions(!lineItemEntity.getLineItemTransactionsById().isEmpty());
        this.setIsLineItem(true);
    }

    public SubLineItem(SubLineItemEntity subLineItemEntity) {
        this.setId(subLineItemEntity.getId());
        this.setName(subLineItemEntity.getName());
        this.setDescription(subLineItemEntity.getDescription());
        this.setUnitPrice(subLineItemEntity.getUnitPrice());
        this.setHasTransactions(!subLineItemEntity.getLineItemTransactionsById().isEmpty());
        this.setIsLineItem(false);
    }
}
