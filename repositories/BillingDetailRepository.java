package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.wirecard.channel.backoffice.entities.BillingDetailEntity;

import java.util.Optional;

@Repository
public interface BillingDetailRepository extends JpaRepository<BillingDetailEntity, Long> {
    Optional<BillingDetailEntity> findByMerchantId(long id);
}
