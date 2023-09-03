package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.RateStructureItemClassificationEntity;

public interface RateStructureItemClassificationRepository extends JpaRepository<RateStructureItemClassificationEntity, Long> {
//    RateStructureItemClassificationEntity findOneById(Long id);
    RateStructureItemClassificationEntity findByCode(String code);
}
