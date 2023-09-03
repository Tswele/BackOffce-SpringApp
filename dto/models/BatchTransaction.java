package za.co.wirecard.channel.backoffice.dto.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import za.co.wirecard.channel.backoffice.entities.BatchTransactionEntity;

import java.sql.Timestamp;

@Data
public class BatchTransaction {
    private long id;
    private String batchUid;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp date;
    private Integer recordsInBatch;
    private Integer recordsProcessed;
    private Boolean completed;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp completedDate;

    public BatchTransaction(BatchTransactionEntity batchTransactionEntity) {
        this.id = batchTransactionEntity.getId();
        this.batchUid = batchTransactionEntity.getBatchUid();
        this.date = batchTransactionEntity.getDate();
        this.recordsInBatch = batchTransactionEntity.getRecordsInBatch();
        this.recordsProcessed = batchTransactionEntity.getRecordsProcessed();
        this.completed = batchTransactionEntity.getCompleted();
        this.completedDate = batchTransactionEntity.getCompletedDate();
    }
}
