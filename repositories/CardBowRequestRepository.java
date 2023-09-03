package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardBowRequestEntity;

public interface CardBowRequestRepository extends JpaRepository<CardBowRequestEntity, Long> {
    CardBowRequestEntity findByTransactionLegId(long id);
}
