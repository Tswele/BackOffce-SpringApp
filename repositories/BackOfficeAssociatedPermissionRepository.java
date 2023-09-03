package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BackOfficeAssociatedPermissionEntity;

public interface BackOfficeAssociatedPermissionRepository extends JpaRepository<BackOfficeAssociatedPermissionEntity, Long> {
}
