package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BillingRunStatusEntity;

public interface BillingRunStatusRepository extends JpaRepository<BillingRunStatusEntity, Long> {
    BillingRunStatusEntity findBillingRunStatusEntityByCode(String code);
}
