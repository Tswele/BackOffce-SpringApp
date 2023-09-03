package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bankserv_authenticate_response", schema = "dbo", catalog = "transaction_db")
public class BankservAuthenticateResponseEntity {
    private long id;
    private Timestamp dateLogged;
    private String errorDesc;
    private String errorNo;
    private String cavv;
    private String xid;
    private String eciFlag;
    private String paresStatus;
    private String signatureVerification;
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
    @Column(name = "error_desc", nullable = true, length = 50)
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
    @Column(name = "cavv", nullable = true, length = 50)
    public String getCavv() {
        return cavv;
    }

    public void setCavv(String cavv) {
        this.cavv = cavv;
    }

    @Basic
    @Column(name = "xid", nullable = true, length = 50)
    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    @Basic
    @Column(name = "eci_flag", nullable = true, length = 2)
    public String getEciFlag() {
        return eciFlag;
    }

    public void setEciFlag(String eciFlag) {
        this.eciFlag = eciFlag;
    }

    @Basic
    @Column(name = "pares_status", nullable = true, length = 50)
    public String getParesStatus() {
        return paresStatus;
    }

    public void setParesStatus(String paresStatus) {
        this.paresStatus = paresStatus;
    }

    @Basic
    @Column(name = "signature_verification", nullable = true, length = 50)
    public String getSignatureVerification() {
        return signatureVerification;
    }

    public void setSignatureVerification(String signatureVerification) {
        this.signatureVerification = signatureVerification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankservAuthenticateResponseEntity that = (BankservAuthenticateResponseEntity) o;

        if (id != that.id) return false;
        if (dateLogged != null ? !dateLogged.equals(that.dateLogged) : that.dateLogged != null) return false;
        if (errorDesc != null ? !errorDesc.equals(that.errorDesc) : that.errorDesc != null) return false;
        if (errorNo != null ? !errorNo.equals(that.errorNo) : that.errorNo != null) return false;
        if (cavv != null ? !cavv.equals(that.cavv) : that.cavv != null) return false;
        if (xid != null ? !xid.equals(that.xid) : that.xid != null) return false;
        if (eciFlag != null ? !eciFlag.equals(that.eciFlag) : that.eciFlag != null) return false;
        if (paresStatus != null ? !paresStatus.equals(that.paresStatus) : that.paresStatus != null) return false;
        if (signatureVerification != null ? !signatureVerification.equals(that.signatureVerification) : that.signatureVerification != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (dateLogged != null ? dateLogged.hashCode() : 0);
        result = 31 * result + (errorDesc != null ? errorDesc.hashCode() : 0);
        result = 31 * result + (errorNo != null ? errorNo.hashCode() : 0);
        result = 31 * result + (cavv != null ? cavv.hashCode() : 0);
        result = 31 * result + (xid != null ? xid.hashCode() : 0);
        result = 31 * result + (eciFlag != null ? eciFlag.hashCode() : 0);
        result = 31 * result + (paresStatus != null ? paresStatus.hashCode() : 0);
        result = 31 * result + (signatureVerification != null ? signatureVerification.hashCode() : 0);
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
