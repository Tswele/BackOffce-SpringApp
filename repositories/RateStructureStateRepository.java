package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.dto.models.RateStructureState;
import za.co.wirecard.channel.backoffice.entities.RateStructureStateEntity;

import java.util.List;

public interface RateStructureStateRepository extends JpaRepository<RateStructureStateEntity, Long> {
//    RateStructureStateEntity findOneById(Long id);
    RateStructureStateEntity findByCode(String code);
    List<RateStructureStateEntity> findByCodeIn(List<String> codes);
}
