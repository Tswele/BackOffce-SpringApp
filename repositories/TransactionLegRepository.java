package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.TransactionEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

import java.util.List;

public interface TransactionLegRepository extends JpaRepository<TransactionLegEntity, Long> {
    List<TransactionLegEntity> findTransactionLegEntitiesByTransactionByTransactionId(TransactionEntity transactionEntity);
}
