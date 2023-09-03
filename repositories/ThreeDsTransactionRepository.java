package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ThreeDsTransactionEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionEntity;

public interface ThreeDsTransactionRepository extends JpaRepository<ThreeDsTransactionEntity, Long> {
    ThreeDsTransactionEntity findByTransactionByTransactionId(TransactionEntity transactionEntity);
}