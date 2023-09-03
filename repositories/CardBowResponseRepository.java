package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardBowResponseEntity;

public interface CardBowResponseRepository extends JpaRepository<CardBowResponseEntity, Long> {
    CardBowResponseEntity findByTransactionLegId(long id);
}
