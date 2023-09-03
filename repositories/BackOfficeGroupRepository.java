package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BackOfficeGroupEntity;

public interface BackOfficeGroupRepository extends JpaRepository<BackOfficeGroupEntity, Long> {
}
