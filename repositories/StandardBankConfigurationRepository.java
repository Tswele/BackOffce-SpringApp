package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.StandardBankConfigurationEntity;

public interface StandardBankConfigurationRepository extends JpaRepository<StandardBankConfigurationEntity, Long>, JpaSpecificationExecutor<StandardBankConfigurationEntity> {
    StandardBankConfigurationEntity findByCardConfigurationId(long id);
}
