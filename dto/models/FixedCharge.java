package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.FixedChargeEntity;
import za.co.wirecard.channel.backoffice.entities.FixedChargeRateStructureLineItemEntity;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FixedCharge {
    BigDecimal value;
    boolean vatInclusive;
    boolean vatable;
    RateStructureLineItem rateStructureLineItem;

    public FixedCharge(FixedChargeEntity fixedChargeEntity) {
        this.vatable = fixedChargeEntity.getVatable();
        this.vatInclusive = fixedChargeEntity.getVatInclusive();
        FixedChargeRateStructureLineItemEntity fixedChargeRateStructureLineItemEntity = fixedChargeEntity.getFixedChargeRateStructureLineItemEntities().iterator().next();
        this.value = fixedChargeRateStructureLineItemEntity.getValue();
        this.rateStructureLineItem = new RateStructureLineItem(fixedChargeRateStructureLineItemEntity.getRateStructureLineItemByRateStructureLineItemId());
    }
}
