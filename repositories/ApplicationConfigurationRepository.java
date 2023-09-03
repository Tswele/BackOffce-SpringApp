package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ApplicationConfigurationEntity;

public interface ApplicationConfigurationRepository extends JpaRepository<ApplicationConfigurationEntity, Long> {
}
