package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.OzowInterfaceConfigurationEntity;
import za.co.wirecard.channel.backoffice.entities.ZapperInterfaceConfigEntity;

public interface OzowInterfaceConfigRepository extends JpaRepository<OzowInterfaceConfigurationEntity, Long> {
    OzowInterfaceConfigurationEntity findByInterfaceId(long id);
}
