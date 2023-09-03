package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BackOfficePermissionEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeRouteEntity;

import java.util.List;

public interface BackOfficeRouteRepository extends JpaRepository<BackOfficeRouteEntity, Long> {
    // List<BackOfficeRouteEntity> findAllByBackOfficePermissionEntitiesIn(List<BackOfficePermissionEntity> backOfficePermissionEntity);
}
