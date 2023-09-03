package za.co.wirecard.channel.backoffice.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "transaction_leg", schema = "dbo", catalog = "transaction_db")
public class TransactionLegEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "date_logged", nullable = false)
    private Timestamp dateLogged;
    @Basic@Column(name = "transaction_value", nullable = true, precision = 2)
    private BigDecimal transactionValue;
    private boolean isAsync;
    @Basic@Column(name = "async_callback_url", nullable = true, length = 250)
    private String asyncCallbackUrl;
    @Basic@Column(name = "sync_wait_time", nullable = true)
    private Integer syncWaitTime;
    @ManyToOne@JoinColumn(name = "transaction_id", referencedColumnName = "id", nullable = false)
    private TransactionEntity transactionByTransactionId;
    @ManyToOne@JoinColumn(name = "transaction_action_id", referencedColumnName = "id", nullable = false)
    private TransactionActionEntity transactionActionByTransactionActionId;

    @Basic
    @Column(name = "is_async", nullable = false)
    public boolean isAsync() {
        return isAsync;
    }

    public void setAsync(boolean async) {
        isAsync = async;
    }

}
