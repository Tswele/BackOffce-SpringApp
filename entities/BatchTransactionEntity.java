package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "batch_transaction")
public class BatchTransactionEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "batch_uid", nullable = false, length = 255)
    private String batchUid;
    @Basic@Column(name = "application_uid", nullable = false, length = 255)
    private String applicationUid;
    @Basic@Column(name = "merchant_uid", nullable = false, length = 255)
    private String merchantUid;
    @Basic@Column(name = "date", nullable = false)
    private Timestamp date;
    @Basic@Column(name = "records_in_batch", nullable = true)
    private Integer recordsInBatch;
    @Basic@Column(name = "records_processed", nullable = true)
    private Integer recordsProcessed;
    @Basic@Column(name = "completed", nullable = true)
    private Boolean completed;
    @Basic@Column(name = "completed_date", nullable = true)
    private Timestamp completedDate;
    @ManyToOne@JoinColumn(name = "payment_type_id", referencedColumnName = "id", nullable = false)
    private PaymentTypeEntity paymentTypeByPaymentTypeId;

}
