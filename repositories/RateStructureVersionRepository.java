package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.RateStructureStateEntity;
import za.co.wirecard.channel.backoffice.entities.RateStructureVersionEntity;

import java.util.ArrayList;
import java.util.List;

public interface RateStructureVersionRepository extends JpaRepository<RateStructureVersionEntity, Long> {
    RateStructureVersionEntity findByRateStructureIdAndRateStructureStateByRateStructureStateId(long rateStructureId, RateStructureStateEntity rateStructureStateEntity);
    ArrayList<RateStructureVersionEntity> findAllByRateStructureId(Long id);
    ArrayList<RateStructureVersionEntity> findAllByRateStructureIdAndRateStructureStateByRateStructureStateId(Long id, RateStructureStateEntity state);
    RateStructureVersionEntity findOneByRateStructureIdAndRateStructureStateByRateStructureStateIdIn(Long id, List<RateStructureStateEntity> rateStructureStateEntityList);
}
