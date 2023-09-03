package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.DisbursementConfigEntity;

public interface DisbursementConfigRepository extends JpaRepository<DisbursementConfigEntity, Long> {
    DisbursementConfigEntity findByInterfaceId(long id);
}
