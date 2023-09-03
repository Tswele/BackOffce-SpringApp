package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.VariableChargeEntity;
import za.co.wirecard.channel.backoffice.entities.VariableChargeRateStructureLineItemEntity;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariableCharge {
    BigDecimal value;
    boolean vatInclusive;
    boolean vatable;
    Long fromAmount;
    Long toAmount;
    RateStructureLineItem rateStructureLineItem;

    public VariableCharge(VariableChargeEntity variableChargeEntity) {
        this.vatable = variableChargeEntity.getVatable();
        this.vatInclusive = variableChargeEntity.getVatInclusive();
        this.fromAmount = variableChargeEntity.getFromAmount();
        this.toAmount = variableChargeEntity.getToAmount();
        VariableChargeRateStructureLineItemEntity variableChargeRateStructureLineItemEntity = variableChargeEntity.getVariableChargeRateStructureLineItemEntities().iterator().next();
        this.value = variableChargeRateStructureLineItemEntity.getValue();
        this.rateStructureLineItem = new RateStructureLineItem(variableChargeRateStructureLineItemEntity.getRateStructureLineItemByRateStructureLineItemId());
    }
}
