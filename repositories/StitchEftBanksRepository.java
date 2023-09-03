package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.StitchEftBanksEntity;

public interface StitchEftBanksRepository extends JpaRepository<StitchEftBanksEntity, Long> {
    StitchEftBanksEntity findById(long id);
}
