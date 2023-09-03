package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.RateStructureItemEntity;

@Data
@NoArgsConstructor
public class RateStructureItem {
    RateStructureItemClassification rateStructureItemClassification;
    RateStructureLineItem rateStructureLineItem;

    public RateStructureItem(RateStructureItemClassification rateStructureItemClassification,
                             RateStructureLineItem rateStructureLineItem) {
        this.rateStructureItemClassification = rateStructureItemClassification;
        this.rateStructureLineItem = rateStructureLineItem;
    }

    public RateStructureItem(RateStructureItemEntity rateStructureItemEntity) {
        this.setRateStructureItemClassification(new RateStructureItemClassification(rateStructureItemEntity.getRateStructureItemClassificationByRateStructureItemClassificationId()));
        this.setRateStructureLineItem(new RateStructureLineItem(rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId()));
    }
}
