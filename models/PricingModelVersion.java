package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.FixedCharge;
import za.co.wirecard.channel.backoffice.dto.models.MinimumBilling;
import za.co.wirecard.channel.backoffice.dto.models.VariableCharge;
import za.co.wirecard.channel.backoffice.entities.FixedChargeEntity;
import za.co.wirecard.channel.backoffice.entities.MinimumBillingEntity;
import za.co.wirecard.channel.backoffice.entities.PricingModelVersionEntity;
import za.co.wirecard.channel.backoffice.entities.VariableChargeEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricingModelVersion {
    private Long version;
    Timestamp last_modified;
    List<FixedCharge> fixedChargeList;
    List<VariableCharge> variableChargeList;
    MinimumBilling minimumBilling;

    public PricingModelVersion(PricingModelVersionEntity pricingModelVersionEntity) {
        this.setVersion(pricingModelVersionEntity.getVersion());
        this.setLast_modified(pricingModelVersionEntity.getLastModified());

        if (!pricingModelVersionEntity.getFixedChargesById().isEmpty()) {
            List<FixedCharge> fixedChargeList = new ArrayList<>();
            ArrayList<FixedChargeEntity> fixedChargeEntityArrayList = new ArrayList<>(pricingModelVersionEntity.getFixedChargesById());
            for (FixedChargeEntity fixedChargeEntity : fixedChargeEntityArrayList) {
                fixedChargeList.add(new FixedCharge(fixedChargeEntity));
            }
            this.fixedChargeList = fixedChargeList;
        }

        if (!pricingModelVersionEntity.getVariableChargesById().isEmpty()) {
            List<VariableCharge> variableChargeList = new ArrayList<>();
            ArrayList<VariableChargeEntity> variableChargeEntityArrayList = new ArrayList<>(pricingModelVersionEntity.getVariableChargesById());
            for (VariableChargeEntity variableChargeEntity : variableChargeEntityArrayList) {
                variableChargeList.add(new VariableCharge(variableChargeEntity));
            }
            this.variableChargeList = variableChargeList;
        }

        if (!pricingModelVersionEntity.getMinimumBillingsById().isEmpty()) {
            MinimumBillingEntity minimumBillingEntity = pricingModelVersionEntity.getMinimumBillingsById().iterator().next();
            this.minimumBilling = new MinimumBilling(minimumBillingEntity);
        }
    }
}
