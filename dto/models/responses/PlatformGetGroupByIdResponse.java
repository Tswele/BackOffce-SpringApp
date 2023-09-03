package za.co.wirecard.channel.backoffice.dto.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.BackOfficeGroupEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeGroupPermissionEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.models.Permission;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeGroupPermissionRepository;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PlatformGetGroupByIdResponse {
    @NotNull
    private long id;
    private String name;
    private String code;
    private BackOfficeUserEntity createdBy;
    private BackOfficeUserEntity modifiedBy;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateCreated;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastModified;
    private List<Permission> permissions;
    private long numberOfUsers;

    public PlatformGetGroupByIdResponse(BackOfficeGroupEntity backOfficeGroupEntity, BackOfficeGroupPermissionRepository backOfficeGroupPermissionRepository) {
        this.id = backOfficeGroupEntity.getId();
        this.name = backOfficeGroupEntity.getName();
        this.code = backOfficeGroupEntity.getCode();
        this.createdBy = backOfficeGroupEntity.getBackOfficeUserByCreatedBy();
        this.modifiedBy = backOfficeGroupEntity.getBackOfficeUserByModifiedBy();
        this.dateCreated = backOfficeGroupEntity.getCreatedDate();
        this.lastModified = backOfficeGroupEntity.getLastModified();
        this.numberOfUsers = backOfficeGroupEntity.getBackOfficeUserEntities().size();
        List<BackOfficeGroupPermissionEntity> backOfficeGroupPermissionEntity = backOfficeGroupPermissionRepository.findAllByBackOfficeGroupByBackOfficeGroupId(backOfficeGroupEntity);
        this.permissions = backOfficeGroupPermissionEntity
                .stream()
                .map(backOfficeGroupPermissionEntity1 -> new Permission(backOfficeGroupPermissionEntity1.getBackOfficePermissionByBackOfficePermissionId()))
                .collect(Collectors.toList());
    }
}
