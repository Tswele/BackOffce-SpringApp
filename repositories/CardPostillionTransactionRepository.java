package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardPostillionTransactionEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface CardPostillionTransactionRepository extends JpaRepository<CardPostillionTransactionEntity, Long> {
    CardPostillionTransactionEntity findCardPostillionTransactionEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
