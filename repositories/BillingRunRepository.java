package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.BillingRunEntity;
import za.co.wirecard.channel.backoffice.entities.BillingRunStatusEntity;

import java.util.List;

public interface BillingRunRepository extends JpaRepository<BillingRunEntity, Long>, JpaSpecificationExecutor<BillingRunEntity> {
    BillingRunEntity findBillingRunEntityByBillingRunStatusByBillingRunStatusId(BillingRunStatusEntity billingRunStatusEntity);
    List<BillingRunEntity> findAllByBillingRunStatusByBillingRunStatusId(BillingRunStatusEntity billingRunStatusEntity);
}
