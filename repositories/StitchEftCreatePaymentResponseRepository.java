package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.StitchEftCreatePaymentResponseEntity;

public interface StitchEftCreatePaymentResponseRepository extends JpaRepository<StitchEftCreatePaymentResponseEntity, Long> {
    StitchEftCreatePaymentResponseEntity findByTransactionLegId(long id);
}
