package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.RateStructureItemClassificationEntity;

@Data
@NoArgsConstructor
public class RateStructureItemClassification {
    String name;
    String description;
    String code;

    public RateStructureItemClassification(RateStructureItemClassificationEntity rateStructureItemClassificationEntity) {
        this.name = rateStructureItemClassificationEntity.getName();
        this.description = rateStructureItemClassificationEntity.getDescription();
        this.code = rateStructureItemClassificationEntity.getCode();
    }
}
