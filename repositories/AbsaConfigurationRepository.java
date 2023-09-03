package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.AbsaConfigurationEntity;

public interface AbsaConfigurationRepository extends JpaRepository<AbsaConfigurationEntity, Long> {
    AbsaConfigurationEntity findByCardConfigurationId(long id);
}
