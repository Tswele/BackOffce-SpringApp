package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BwConfigurationEntity;

public interface BwConfigurationRepository extends JpaRepository<BwConfigurationEntity, Long> {
    BwConfigurationEntity findByCardConfigurationId(long id);
}
