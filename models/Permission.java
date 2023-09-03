package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.entities.BackOfficePermissionEntity;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

public class Permission {
    private long id;
    private String name;
    private String description;
    private String code;
    private Date lastModified;

    public Permission(BackOfficePermissionEntity backOfficePermissionEntity) {
        this.id = backOfficePermissionEntity.getId();
        this.name = backOfficePermissionEntity.getName();
        this.description = backOfficePermissionEntity.getDescription();
        this.code = backOfficePermissionEntity.getCode();
        this.lastModified = backOfficePermissionEntity.getLastModified();
    }
}
