package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardIveriTransactionEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface CardIveriTransactionRepository extends JpaRepository<CardIveriTransactionEntity, Long> {
    CardIveriTransactionEntity findCardIveriTransactionEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
