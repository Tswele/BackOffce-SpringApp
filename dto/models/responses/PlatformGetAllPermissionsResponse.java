/*package za.co.wirecard.channel.merchantportal.dto.models.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.platform.supporting.usermanagement.entities.AuthPermissionEntity;
import za.co.wirecard.platform.supporting.usermanagement.models.Permission;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PlatformGetAllPermissionsResponse extends Permission {

    @NotNull
    private long id;
    private Date lastModified;

    public PlatformGetAllPermissionsResponse(AuthPermissionEntity permissionEntity) {
        super(permissionEntity);
        this.setId(permissionEntity.getId());
        this.setLastModified(permissionEntity.getLastModified());
    }
}
*/