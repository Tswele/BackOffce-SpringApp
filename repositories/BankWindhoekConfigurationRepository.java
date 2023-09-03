package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.BankWindhoekConfigurationEntity;

public interface BankWindhoekConfigurationRepository extends JpaRepository<BankWindhoekConfigurationEntity, Long>, JpaSpecificationExecutor<BankWindhoekConfigurationEntity> {
    BankWindhoekConfigurationEntity findByCardConfigurationId(long id);
}
