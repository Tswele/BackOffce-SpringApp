package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.wirecard.channel.backoffice.entities.BankservConfigurationEntity;

@Repository
public interface BankservConfigurationRepository extends JpaRepository<BankservConfigurationEntity, Long> {
    BankservConfigurationEntity findByCardConfigurationId(long bankservConfigurationEntityId);
}
