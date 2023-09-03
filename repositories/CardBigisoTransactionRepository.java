package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardBigisoTransactionEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface CardBigisoTransactionRepository extends JpaRepository<CardBigisoTransactionEntity, Long> {
    CardBigisoTransactionEntity findCardBigisoTransactionEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
