package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.MinimumBillingEntity;
import za.co.wirecard.channel.backoffice.entities.MinimumBillingRateStructureLineItemEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinimumBilling {
    BigDecimal value;
    boolean vatInclusive;
    boolean vatable;
    List<RateStructureLineItem> rateStructureLineItem;

    public MinimumBilling(MinimumBillingEntity minimumBillingEntity) {
        this.value = minimumBillingEntity.getValue();
        this.vatable = minimumBillingEntity.getVatable();
        this.vatInclusive = minimumBillingEntity.getVatInclusive();
        List<RateStructureLineItem> rateStructureLineItemList = new ArrayList<>();
        List<MinimumBillingRateStructureLineItemEntity> minimumBillingRateStructureLineItemEntityList = new ArrayList<>(minimumBillingEntity.getMinimumBillingRateStructureLineItemEntities());
        for (MinimumBillingRateStructureLineItemEntity minimumBillingRateStructureLineItemEntity : minimumBillingRateStructureLineItemEntityList) {
            rateStructureLineItemList.add(new RateStructureLineItem(minimumBillingRateStructureLineItemEntity.getRateStructureLineItemByRateStructureLineItemId()));
        }
        this.rateStructureLineItem = rateStructureLineItemList;
    }
}
