package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.StitchEftCreatePaymentRequestEntity;

public interface StitchEftCreatePaymentRequestRepository extends JpaRepository<StitchEftCreatePaymentRequestEntity, Long> {
    StitchEftCreatePaymentRequestEntity findByTransactionLegId(long id);
}
