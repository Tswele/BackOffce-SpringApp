package za.co.wirecard.channel.backoffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import za.co.wirecard.channel.backoffice.entities.ApplicationEntity;

@Data
@NoArgsConstructor
public class ApplicationType {

    private long id;
    private String name;
    private String applicationUid;

    public ApplicationType(ApplicationEntity applicationEntity) {
        this.id = applicationEntity.getId();
        this.name = applicationEntity.getName();
        this.applicationUid = applicationEntity.getApplicationUid();
    }
}
