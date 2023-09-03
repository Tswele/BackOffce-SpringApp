package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bankserv_authenticate_request", schema = "dbo", catalog = "transaction_db")
public class BankservAuthenticateRequestEntity {
    private long id;
    private Timestamp dateLogged;
    private String version;
    private String msgType;
    private String processorId;
    private String merchantId;
    private String transactionType;
    private String transactionPwd;
    private String transactionId;
    private String paresPayload;
    private TransactionLegEntity transactionLegByTransactionLegId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date_logged", nullable = false)
    public Timestamp getDateLogged() {
        return dateLogged;
    }

    public void setDateLogged(Timestamp dateLogged) {
        this.dateLogged = dateLogged;
    }

    @Basic
    @Column(name = "version", nullable = false, length = 50)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "msg_type", nullable = false, length = 50)
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Basic
    @Column(name = "processor_id", nullable = false, length = 50)
    public String getProcessorId() {
        return processorId;
    }

    public void setProcessorId(String processorId) {
        this.processorId = processorId;
    }

    @Basic
    @Column(name = "merchant_id", nullable = false, length = 50)
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Basic
    @Column(name = "transaction_type", nullable = false, length = 50)
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Basic
    @Column(name = "transaction_pwd", nullable = false, length = 50)
    public String getTransactionPwd() {
        return transactionPwd;
    }

    public void setTransactionPwd(String transactionPwd) {
        this.transactionPwd = transactionPwd;
    }

    @Basic
    @Column(name = "transaction_id", nullable = false, length = 50)
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "pares_payload", nullable = false, length = 2147483647)
    public String getParesPayload() {
        return paresPayload;
    }

    public void setParesPayload(String paresPayload) {
        this.paresPayload = paresPayload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankservAuthenticateRequestEntity that = (BankservAuthenticateRequestEntity) o;

        if (id != that.id) return false;
        if (dateLogged != null ? !dateLogged.equals(that.dateLogged) : that.dateLogged != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (msgType != null ? !msgType.equals(that.msgType) : that.msgType != null) return false;
        if (processorId != null ? !processorId.equals(that.processorId) : that.processorId != null) return false;
        if (merchantId != null ? !merchantId.equals(that.merchantId) : that.merchantId != null) return false;
        if (transactionType != null ? !transactionType.equals(that.transactionType) : that.transactionType != null)
            return false;
        if (transactionPwd != null ? !transactionPwd.equals(that.transactionPwd) : that.transactionPwd != null)
            return false;
        if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
            return false;
        if (paresPayload != null ? !paresPayload.equals(that.paresPayload) : that.paresPayload != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (dateLogged != null ? dateLogged.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (msgType != null ? msgType.hashCode() : 0);
        result = 31 * result + (processorId != null ? processorId.hashCode() : 0);
        result = 31 * result + (merchantId != null ? merchantId.hashCode() : 0);
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        result = 31 * result + (transactionPwd != null ? transactionPwd.hashCode() : 0);
        result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
        result = 31 * result + (paresPayload != null ? paresPayload.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "transaction_leg_id", referencedColumnName = "id", nullable = false)
    public TransactionLegEntity getTransactionLegByTransactionLegId() {
        return transactionLegByTransactionLegId;
    }

    public void setTransactionLegByTransactionLegId(TransactionLegEntity transactionLegByTransactionLegId) {
        this.transactionLegByTransactionLegId = transactionLegByTransactionLegId;
    }
}
