package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.FnbConfigurationEntity;

public interface FnbConfigurationRepository extends JpaRepository<FnbConfigurationEntity, Long> {
    FnbConfigurationEntity findByCardConfigurationId(long id);
}
