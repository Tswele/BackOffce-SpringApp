package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.RateStructureItemEntity;
import za.co.wirecard.channel.backoffice.entities.RateStructureVersionEntity;
import za.co.wirecard.channel.backoffice.models.BackOfficeUser;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RateStructureVersion {
    Timestamp last_modified;
    Long version;
    BillingFrequency billingFrequency;
    RateStructureState rateStructureState;
    BackOfficeUser modified_by;
    List<RateStructureItem> rateStructureItemList;

    public RateStructureVersion(RateStructureVersionEntity rateStructureVersionEntity,
                                BillingFrequency billingFrequency,
                                RateStructureState rateStructureState,
                                BackOfficeUser backOfficeUser) {
        this.last_modified = rateStructureVersionEntity.getLastModified();
        this.version = rateStructureVersionEntity.getVersion();
        this.billingFrequency = billingFrequency;
        this.rateStructureState = rateStructureState;
        this.modified_by = backOfficeUser;
    }

    public RateStructureVersion(RateStructureVersionEntity rateStructureVersionEntity) {
        this.setLast_modified(rateStructureVersionEntity.getLastModified());
        this.setVersion(rateStructureVersionEntity.getVersion());
        this.setRateStructureState(new RateStructureState(rateStructureVersionEntity.getRateStructureStateByRateStructureStateId()));
        this.setModified_by(new BackOfficeUser(rateStructureVersionEntity.getBackOfficeUserByLastModifiedBy()));
        List<RateStructureItem> rateStructureItems = new ArrayList<>();
        List<RateStructureItemEntity> rateStructureItemEntities = new ArrayList<>(rateStructureVersionEntity.getRateStructureItemsById());
        for (RateStructureItemEntity rateStructureItemEntity: rateStructureItemEntities) {
            rateStructureItems.add(new RateStructureItem(rateStructureItemEntity));
        }
        this.setRateStructureItemList(rateStructureItems);
    }
}
