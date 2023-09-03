package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.StitchEftGetPaymentResponseEntity;

public interface StitchEftGetPaymentResponseRepository extends JpaRepository<StitchEftGetPaymentResponseEntity, Long> {
    StitchEftGetPaymentResponseEntity findByTransactionLegId(long id);
}
