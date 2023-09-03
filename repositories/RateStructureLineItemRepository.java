package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.RateStructureLineItemEntity;

public interface RateStructureLineItemRepository extends JpaRepository<RateStructureLineItemEntity, Long> {
//    RateStructureLineItemEntity findOneById(Long id);
    RateStructureLineItemEntity findByCode(String code);
}
