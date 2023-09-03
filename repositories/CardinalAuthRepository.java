package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardinalAuthEntity;

public interface CardinalAuthRepository extends JpaRepository<CardinalAuthEntity, Long> {
    CardinalAuthEntity findByThreeDsTransactionId(long id);
}
