package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.RateStructureItemEntity;

import java.util.ArrayList;

public interface RateStructureItemRepository extends JpaRepository<RateStructureItemEntity, Long> {
//    ArrayList<RateStructureItemEntity> findAllByRateStructureVersionId(Long id);
}
