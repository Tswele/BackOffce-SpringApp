package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BatchTransactionEntity;
import za.co.wirecard.channel.backoffice.entities.CardBatchRecordEntity;

import java.util.List;

public interface CardBatchRecordRepository extends JpaRepository<CardBatchRecordEntity, Long> {
    Page<CardBatchRecordEntity> findCardBatchRecordEntitiesByBatchTransactionByBatchId(BatchTransactionEntity batchTransactionEntity, Pageable pageable);
    List<CardBatchRecordEntity> findCardBatchRecordEntitiesByBatchTransactionByBatchId(BatchTransactionEntity batchTransactionEntity);
}
