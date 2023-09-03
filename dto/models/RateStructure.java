package za.co.wirecard.channel.backoffice.dto.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.RateStructureEntity;
import za.co.wirecard.channel.backoffice.entities.RateStructureVersionEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RateStructure {
    Long id;
    String name;
    String description;
    String code;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    Timestamp lastModified;
    List<RateStructureVersion> rateStructureVersions;
    Long version;
    String state;

    public RateStructure(RateStructureEntity rateStructureEntity, String state, Long version) {
        this.id = rateStructureEntity.getId();
        this.name = rateStructureEntity.getName();
        this.description = rateStructureEntity.getDescription();
        this.code = rateStructureEntity.getCode();
        this.lastModified = rateStructureEntity.getLastModified();
        this.version = version;
        this.state = state;
    }

    public RateStructure(RateStructureEntity rateStructureEntity, Long version, String state) {
        this.id = rateStructureEntity.getId();
        this.name = rateStructureEntity.getName();
        this.description = rateStructureEntity.getDescription();
        this.code = rateStructureEntity.getCode();
        this.lastModified = rateStructureEntity.getLastModified();
        List<RateStructureVersionEntity> rateStructureVersionEntities = new ArrayList<>(rateStructureEntity.getRateStructureVersionsById());
        List<RateStructureVersion> rateStructureVersions = new ArrayList<>();
        for (RateStructureVersionEntity rateStructureVersionEntity: rateStructureVersionEntities) {
            rateStructureVersions.add(new RateStructureVersion(rateStructureVersionEntity));
        }
        this.rateStructureVersions = rateStructureVersions;
        this.version = version;
        this.state = state;
    }

    public RateStructure(RateStructureEntity rateStructureEntity) {
        this.id = rateStructureEntity.getId();
        this.name = rateStructureEntity.getName();
        this.description = rateStructureEntity.getDescription();
        this.code = rateStructureEntity.getCode();
        this.lastModified = rateStructureEntity.getLastModified();
        List<RateStructureVersionEntity> rateStructureVersionEntities = new ArrayList<>(rateStructureEntity.getRateStructureVersionsById());
        List<RateStructureVersion> rateStructureVersions = new ArrayList<>();
        for (RateStructureVersionEntity rateStructureVersionEntity: rateStructureVersionEntities) {
            rateStructureVersions.add(new RateStructureVersion(rateStructureVersionEntity));
        }
        this.rateStructureVersions = rateStructureVersions;
    }
}
