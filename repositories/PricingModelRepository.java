package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.PricingModelEntity;

import java.util.List;

public interface PricingModelRepository extends JpaRepository<PricingModelEntity, Long>, JpaSpecificationExecutor<PricingModelEntity> {
    List<PricingModelEntity> findAllByRateStructureId(Long id);
    List<PricingModelEntity> findAllByRateStructureIdAndGlobalPricingModel(Long id, Boolean globalPricingModel);
}
