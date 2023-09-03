package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.OzowEftPaymentUpdateEntity;

public interface OzowEftPaymentUpdateRepository extends JpaRepository<OzowEftPaymentUpdateEntity, Long> {
    OzowEftPaymentUpdateEntity findByTransactionLegId(long id);
}
