package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.StitchInterfaceConfigurationEntity;

public interface StitchInterfaceConfigRepository extends JpaRepository<StitchInterfaceConfigurationEntity, Long> {
    StitchInterfaceConfigurationEntity findByInterfaceId(long id);
}
