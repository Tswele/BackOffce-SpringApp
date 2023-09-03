package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ConfigurationTypeEntity;

public interface ConfigurationTypeRepository extends JpaRepository<ConfigurationTypeEntity, Long> {
}
