package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.LineItemEntity;
import za.co.wirecard.channel.backoffice.entities.SubLineItemEntity;
import za.co.wirecard.channel.backoffice.models.SubLineItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLineItemHistoryResponse {
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private List<SubLineItem> subLineItemList;

    public GetLineItemHistoryResponse(LineItemEntity lineItemEntity) {
        this.setName(lineItemEntity.getName());
        this.setDescription(lineItemEntity.getDescription());
        this.setUnitPrice(lineItemEntity.getUnitPrice());
        List<SubLineItem> subLineItemList = new ArrayList<>();
        if (lineItemEntity.getSubLineItemsById().isEmpty()) {
            subLineItemList.add(new SubLineItem(lineItemEntity));
        } else {
            for (SubLineItemEntity subLineItemEntity: lineItemEntity.getSubLineItemsById()) {
                subLineItemList.add(new SubLineItem(subLineItemEntity));
            }
        }
        this.setSubLineItemList(subLineItemList);
    }
}
