package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardIveriResponseEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface CardIveriResponseRepository extends JpaRepository<CardIveriResponseEntity, Long> {
    CardIveriResponseEntity findCardIveriResponseEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
