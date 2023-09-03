package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@Table(name = "\"transaction\"", schema = "dbo", catalog = "transaction_db")
public class TransactionEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "merchant_id", insertable = false, updatable = false)
    private long merchantId;
    @Basic@Column(name = "transaction_uid", nullable = false, length = 50)
    private String transactionUid;
    @Basic@Column(name = "initiation_date", nullable = false)
    private Timestamp initiationDate;
    @Basic@Column(name = "last_updated_date", nullable = false)
    private Timestamp lastUpdatedDate;
    @Basic@Column(name = "completed_date", nullable = true)
    private Timestamp completedDate;
    @Basic@Column(name = "transaction_value", nullable = false, precision = 2)
    private BigDecimal transactionValue;
    @Basic@Column(name = "refund_value", nullable = true, precision = 2)
    private BigDecimal refundValue;
    @Basic@Column(name = "authorised_value", nullable = true, precision = 2)
    private BigDecimal authorisedValue;
    @Basic@Column(name = "settled_value", nullable = true, precision = 2)
    private BigDecimal settledValue;
    private boolean isCompleted;
    @Basic@Column(name = "merchant_reference", nullable = false, length = 100)
    private String merchantReference;
    @Column(name = "interface_id", insertable = false, updatable = false)
    private long interfaceId;
    @Basic@Column(name = "error_code", nullable = true, length = 100)
    private String errorCode;
    @Basic@Column(name = "error_message", nullable = true, length = 250)
    private String errorMessage;
    @Column(name = "payment_type_id", insertable = false, updatable = false)
    private long paymentTypeId;
    @Column(name = "transaction_state_id", insertable = false, updatable = false)
    private long transactionStateId;
    @ManyToOne@JoinColumn(name = "payment_type_id", referencedColumnName = "id", nullable = false)
    private PaymentTypeEntity paymentTypeByPaymentTypeId;
    @ManyToOne@JoinColumn(name = "gateway_id", referencedColumnName = "id", nullable = false)
    private GatewayEntity gatewayByGatewayId;
    @ManyToOne@JoinColumn(name = "transaction_state_id", referencedColumnName = "id", nullable = false)
    private TransactionStateEntity transactionStateByTransactionStateId;
    @ManyToOne@JoinColumn(name = "merchant_id", referencedColumnName = "id", nullable = false)
    private MerchantEntity merchantByMerchantId;
    @ManyToOne@JoinColumn(name = "related_transaction_id", referencedColumnName = "id")
    private TransactionEntity transactionByRelatedTransactionId;
    @ManyToOne@JoinColumn(name = "interface_id", referencedColumnName = "id", nullable = false)
    private InterfaceEntity interfaceByInterfaceId;
    @Basic@Column(name = "originating_transaction_id", nullable = true, length = 50)
    private String originatingTransactionId;
    @Basic@Column(name = "purchaser_full_name", nullable = true, length = 50)
    private String purchaserFullName;
    @Basic@Column(name = "purchaser_email", nullable = true, length = 50)
    private String purchaserEmail;
    @Basic@Column(name = "purchaser_msisdn", nullable = true, length = 50)
    private String purchaserMsisdn;
    @ManyToOne@JoinColumn(name = "payment_subtype_id", referencedColumnName = "id")
    private PaymentSubtypeEntity paymentSubtypeByPaymentSubtypeId;
    @ManyToOne@JoinColumn(name = "three_ds_merchant_type", referencedColumnName = "id")
    private TdsMerchantTypeEntity tdsMerchantTypeByThreeDsMerchantType;

    @OneToMany(mappedBy = "transactionByTransactionId")
    private Collection<CardTransactionEntity> cardTransactionsById;

    //    @OneToOne
//    @JoinTable(
//            name = "card_transaction",
//            joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "card_number")
//    )
//    @OneToOne
//    @JoinColumn(name = "id", referencedColumnName = "transaction_id")
//    private CardTransactionEntity cardTransactionEntity;

    public TransactionEntity() {
    }

    @Basic
    @Column(name = "is_completed", nullable = false)
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TransactionEntity;
    }

}
