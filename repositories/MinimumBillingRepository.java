package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.MinimumBillingEntity;

public interface MinimumBillingRepository extends JpaRepository<MinimumBillingEntity, Long>, JpaSpecificationExecutor<MinimumBillingEntity> {
}
