package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bankserv_lookup_response", schema = "dbo", catalog = "transaction_db")
public class BankservLookupResponseEntity {
    private long id;
    private Timestamp dateLogged;
    private String errorDesc;
    private String errorNo;
    private String transactionId;
    private String payload;
    private String enrolled;
    private String orderId;
    private String acsUrl;
    private String eciFlag;
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
    @Column(name = "error_desc", nullable = true, length = 100)
    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    @Basic
    @Column(name = "error_no", nullable = true, length = 50)
    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    @Basic
    @Column(name = "transaction_id", nullable = true, length = 100)
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "payload", nullable = true, length = 2147483647)
    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Basic
    @Column(name = "enrolled", nullable = true, length = 50)
    public String getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(String enrolled) {
        this.enrolled = enrolled;
    }

    @Basic
    @Column(name = "order_id", nullable = true, length = 50)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "acs_url", nullable = true, length = 2147483647)
    public String getAcsUrl() {
        return acsUrl;
    }

    public void setAcsUrl(String acsUrl) {
        this.acsUrl = acsUrl;
    }

    @Basic
    @Column(name = "eci_flag", nullable = true, length = 50)
    public String getEciFlag() {
        return eciFlag;
    }

    public void setEciFlag(String eciFlag) {
        this.eciFlag = eciFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankservLookupResponseEntity that = (BankservLookupResponseEntity) o;

        if (id != that.id) return false;
        if (dateLogged != null ? !dateLogged.equals(that.dateLogged) : that.dateLogged != null) return false;
        if (errorDesc != null ? !errorDesc.equals(that.errorDesc) : that.errorDesc != null) return false;
        if (errorNo != null ? !errorNo.equals(that.errorNo) : that.errorNo != null) return false;
        if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
            return false;
        if (payload != null ? !payload.equals(that.payload) : that.payload != null) return false;
        if (enrolled != null ? !enrolled.equals(that.enrolled) : that.enrolled != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (acsUrl != null ? !acsUrl.equals(that.acsUrl) : that.acsUrl != null) return false;
        if (eciFlag != null ? !eciFlag.equals(that.eciFlag) : that.eciFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (dateLogged != null ? dateLogged.hashCode() : 0);
        result = 31 * result + (errorDesc != null ? errorDesc.hashCode() : 0);
        result = 31 * result + (errorNo != null ? errorNo.hashCode() : 0);
        result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        result = 31 * result + (enrolled != null ? enrolled.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (acsUrl != null ? acsUrl.hashCode() : 0);
        result = 31 * result + (eciFlag != null ? eciFlag.hashCode() : 0);
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
