package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "three_ds_transaction", schema = "dbo", catalog = "transaction_db")
public class ThreeDsTransactionEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "auth_required", nullable = false)
    private boolean authRequired;
    @Basic@Column(name = "amount", nullable = false, precision = 2)
    private BigDecimal amount;
    @Basic@Column(name = "description", nullable = false, length = 50)
    private String description;
    @Basic@Column(name = "version", nullable = true)
    private Short version;
    @Basic@Column(name = "callback_url", nullable = true)
    private String callbackUrl;
    @Basic@Column(name = "eci", nullable = true)
    private String eci;
    @Basic@Column(name = "security_method", nullable = true)
    private Short securityMethod;
    @Basic@Column(name = "date_authenticated", nullable = true)
    private Timestamp dateAuthenticated;
    @Basic@Column(name = "cavv", nullable = true)
    private String  cavv;
    @Basic@Column(name = "xid", nullable = true)
    private String  xid;
    @Basic@Column(name = "previous_authentication", nullable = true)
    private Boolean previousAuthentication;
    @Basic@Column(name = "ds_transaction_id", nullable = true)
    private String dsTransactionId;
    @Basic@Column(name = "merchant_md", nullable = true)
    private String merchantMd;
    @ManyToOne@JoinColumn(name = "transaction_id", referencedColumnName = "id", nullable = false)
    private TransactionEntity transactionByTransactionId;

}
