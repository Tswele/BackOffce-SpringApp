package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.StitchEftBankAccountEntity;

public interface StitchEftBankAccountRepository extends JpaRepository<StitchEftBankAccountEntity, Long> {
    StitchEftBankAccountEntity findByStitchInterfaceConfigurationId(long id);
}
