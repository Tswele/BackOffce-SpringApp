package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.PricingModelVersionEntity;

public interface PricingModelVersionRepository extends JpaRepository<PricingModelVersionEntity, Long>, JpaSpecificationExecutor<PricingModelVersionEntity> {
    PricingModelVersionEntity findFirstByPricingModelIdOrderByVersionDesc(Long id);
}
