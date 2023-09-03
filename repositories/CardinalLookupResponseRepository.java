package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardinalLookupResponseEntity;

public interface CardinalLookupResponseRepository extends JpaRepository<CardinalLookupResponseEntity, Long> {
    CardinalLookupResponseEntity findByTransactionLegId(long id);
}
