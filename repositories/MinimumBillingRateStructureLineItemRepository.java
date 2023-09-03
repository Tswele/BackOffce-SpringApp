package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.MinimumBillingRateStructureLineItemEntity;

public interface MinimumBillingRateStructureLineItemRepository extends JpaRepository<MinimumBillingRateStructureLineItemEntity, Long>, JpaSpecificationExecutor<MinimumBillingRateStructureLineItemEntity> {
}
