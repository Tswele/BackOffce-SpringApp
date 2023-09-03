package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.RateStructureStateEntity;

@Data
@NoArgsConstructor
public class RateStructureState {
    String name;
    String description;
    String code;

    public RateStructureState(RateStructureStateEntity rateStructureStateEntity) {
        this.name = rateStructureStateEntity.getName();
        this.description = rateStructureStateEntity.getDescription();
        this.code = rateStructureStateEntity.getCode();
    }
}
