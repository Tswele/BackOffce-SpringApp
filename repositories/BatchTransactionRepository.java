package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.BatchTransactionEntity;

public interface BatchTransactionRepository extends JpaRepository<BatchTransactionEntity, Long>, JpaSpecificationExecutor<BatchTransactionEntity> {
    BatchTransactionEntity findBatchTransactionEntityByBatchUid(String batchUid);
}
