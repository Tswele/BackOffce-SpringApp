package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BackOfficePermissionEntity;

import java.util.List;

public interface BackOfficePermissionRepository extends JpaRepository<BackOfficePermissionEntity, Long> {
    BackOfficePermissionEntity findByCode(String code);
}
