package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BackOfficeGroupEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeGroupPermissionEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficePermissionEntity;

import java.util.List;

public interface BackOfficeGroupPermissionRepository extends JpaRepository<BackOfficeGroupPermissionEntity, Long> {
    List<BackOfficeGroupPermissionEntity> findAllByBackOfficeGroupByBackOfficeGroupId(BackOfficeGroupEntity backOfficeGroupEntity);
    void deleteAllByBackOfficeGroupByBackOfficeGroupId(BackOfficeGroupEntity backOfficeGroupEntity);
    List<BackOfficeGroupPermissionEntity> findAllByBackOfficePermissionByBackOfficePermissionId(BackOfficePermissionEntity backOfficePermissionEntity);
}
