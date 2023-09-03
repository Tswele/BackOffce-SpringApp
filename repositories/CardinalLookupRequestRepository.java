package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardinalLookupRequestEntity;

public interface CardinalLookupRequestRepository extends JpaRepository<CardinalLookupRequestEntity, Long> {
    CardinalLookupRequestEntity findByTransactionLegId(long id);
}
