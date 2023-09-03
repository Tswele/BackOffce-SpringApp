package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;

@Entity
@Table(name = "three_ds_auth", schema = "dbo", catalog = "transaction_db")
public class ThreeDsAuthEntity {
    private long id;
    private String version;
    private String merchantId;
    private String xid;
    private Integer mdStatus;
    private String mdErrorMsg;
    private String veresEnrolledStatus;
    private String paresTxStatus;
    private String iReqCode;
    private String iReqDetail;
    private String vendorCode;
    private String eci;
    private String cavv;
    private String cavvAlgorithm;
    private String md;
    private String paResVerified;
    private String paResSyntaxOk;
    private String protocol;
    private String cardType;
    private String transStatus;
    private String dsTransId;
    private String acsTransId;
    private String acsReferenceNumber;
    private String authTimestamp;
    private String authenticationType;
    private String sId;
    private ThreeDsTransactionEntity threeDsTransactionByThreeDsTransactionId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "version", nullable = true, length = 2147483647)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "merchant_id", nullable = true, length = 2147483647)
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Basic
    @Column(name = "xid", nullable = true, length = 2147483647)
    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    @Basic
    @Column(name = "md_status", nullable = true)
    public Integer getMdStatus() {
        return mdStatus;
    }

    public void setMdStatus(Integer mdStatus) {
        this.mdStatus = mdStatus;
    }

    @Basic
    @Column(name = "md_error_msg", nullable = true, length = 2147483647)
    public String getMdErrorMsg() {
        return mdErrorMsg;
    }

    public void setMdErrorMsg(String mdErrorMsg) {
        this.mdErrorMsg = mdErrorMsg;
    }

    @Basic
    @Column(name = "veres_enrolled_status", nullable = true, length = 2147483647)
    public String getVeresEnrolledStatus() {
        return veresEnrolledStatus;
    }

    public void setVeresEnrolledStatus(String veresEnrolledStatus) {
        this.veresEnrolledStatus = veresEnrolledStatus;
    }

    @Basic
    @Column(name = "pares_tx_status", nullable = true, length = 2147483647)
    public String getParesTxStatus() {
        return paresTxStatus;
    }

    public void setParesTxStatus(String paresTxStatus) {
        this.paresTxStatus = paresTxStatus;
    }

    @Basic
    @Column(name = "i_req_code", nullable = true, length = 2147483647)
    public String getiReqCode() {
        return iReqCode;
    }

    public void setiReqCode(String iReqCode) {
        this.iReqCode = iReqCode;
    }

    @Basic
    @Column(name = "i_req_detail", nullable = true, length = 2147483647)
    public String getiReqDetail() {
        return iReqDetail;
    }

    public void setiReqDetail(String iReqDetail) {
        this.iReqDetail = iReqDetail;
    }

    @Basic
    @Column(name = "vendor_code", nullable = true, length = 2147483647)
    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Basic
    @Column(name = "eci", nullable = true, length = 2147483647)
    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    @Basic
    @Column(name = "cavv", nullable = true, length = 2147483647)
    public String getCavv() {
        return cavv;
    }

    public void setCavv(String cavv) {
        this.cavv = cavv;
    }

    @Basic
    @Column(name = "cavv_algorithm", nullable = true, length = 2147483647)
    public String getCavvAlgorithm() {
        return cavvAlgorithm;
    }

    public void setCavvAlgorithm(String cavvAlgorithm) {
        this.cavvAlgorithm = cavvAlgorithm;
    }

    @Basic
    @Column(name = "md", nullable = true, length = 2147483647)
    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    @Basic
    @Column(name = "pa_res_verified", nullable = true, length = 2147483647)
    public String getPaResVerified() {
        return paResVerified;
    }

    public void setPaResVerified(String paResVerified) {
        this.paResVerified = paResVerified;
    }

    @Basic
    @Column(name = "pa_res_syntax_ok", nullable = true, length = 2147483647)
    public String getPaResSyntaxOk() {
        return paResSyntaxOk;
    }

    public void setPaResSyntaxOk(String paResSyntaxOk) {
        this.paResSyntaxOk = paResSyntaxOk;
    }

    @Basic
    @Column(name = "protocol", nullable = true, length = 2147483647)
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Basic
    @Column(name = "card_type", nullable = true, length = 2147483647)
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Basic
    @Column(name = "trans_status", nullable = true, length = 2147483647)
    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    @Basic
    @Column(name = "ds_trans_id", nullable = true, length = 2147483647)
    public String getDsTransId() {
        return dsTransId;
    }

    public void setDsTransId(String dsTransId) {
        this.dsTransId = dsTransId;
    }

    @Basic
    @Column(name = "acs_trans_id", nullable = true, length = 2147483647)
    public String getAcsTransId() {
        return acsTransId;
    }

    public void setAcsTransId(String acsTransId) {
        this.acsTransId = acsTransId;
    }

    @Basic
    @Column(name = "acs_reference_number", nullable = true, length = 2147483647)
    public String getAcsReferenceNumber() {
        return acsReferenceNumber;
    }

    public void setAcsReferenceNumber(String acsReferenceNumber) {
        this.acsReferenceNumber = acsReferenceNumber;
    }

    @Basic
    @Column(name = "auth_timestamp", nullable = true, length = 2147483647)
    public String getAuthTimestamp() {
        return authTimestamp;
    }

    public void setAuthTimestamp(String authTimestamp) {
        this.authTimestamp = authTimestamp;
    }

    @Basic
    @Column(name = "authentication_type", nullable = true, length = 2147483647)
    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    @Basic
    @Column(name = "s_id", nullable = true, length = 2147483647)
    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThreeDsAuthEntity that = (ThreeDsAuthEntity) o;

        if (id != that.id) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (merchantId != null ? !merchantId.equals(that.merchantId) : that.merchantId != null) return false;
        if (xid != null ? !xid.equals(that.xid) : that.xid != null) return false;
        if (mdStatus != null ? !mdStatus.equals(that.mdStatus) : that.mdStatus != null) return false;
        if (mdErrorMsg != null ? !mdErrorMsg.equals(that.mdErrorMsg) : that.mdErrorMsg != null) return false;
        if (veresEnrolledStatus != null ? !veresEnrolledStatus.equals(that.veresEnrolledStatus) : that.veresEnrolledStatus != null)
            return false;
        if (paresTxStatus != null ? !paresTxStatus.equals(that.paresTxStatus) : that.paresTxStatus != null)
            return false;
        if (iReqCode != null ? !iReqCode.equals(that.iReqCode) : that.iReqCode != null) return false;
        if (iReqDetail != null ? !iReqDetail.equals(that.iReqDetail) : that.iReqDetail != null) return false;
        if (vendorCode != null ? !vendorCode.equals(that.vendorCode) : that.vendorCode != null) return false;
        if (eci != null ? !eci.equals(that.eci) : that.eci != null) return false;
        if (cavv != null ? !cavv.equals(that.cavv) : that.cavv != null) return false;
        if (cavvAlgorithm != null ? !cavvAlgorithm.equals(that.cavvAlgorithm) : that.cavvAlgorithm != null)
            return false;
        if (md != null ? !md.equals(that.md) : that.md != null) return false;
        if (paResVerified != null ? !paResVerified.equals(that.paResVerified) : that.paResVerified != null)
            return false;
        if (paResSyntaxOk != null ? !paResSyntaxOk.equals(that.paResSyntaxOk) : that.paResSyntaxOk != null)
            return false;
        if (protocol != null ? !protocol.equals(that.protocol) : that.protocol != null) return false;
        if (cardType != null ? !cardType.equals(that.cardType) : that.cardType != null) return false;
        if (transStatus != null ? !transStatus.equals(that.transStatus) : that.transStatus != null) return false;
        if (dsTransId != null ? !dsTransId.equals(that.dsTransId) : that.dsTransId != null) return false;
        if (acsTransId != null ? !acsTransId.equals(that.acsTransId) : that.acsTransId != null) return false;
        if (acsReferenceNumber != null ? !acsReferenceNumber.equals(that.acsReferenceNumber) : that.acsReferenceNumber != null)
            return false;
        if (authTimestamp != null ? !authTimestamp.equals(that.authTimestamp) : that.authTimestamp != null)
            return false;
        if (authenticationType != null ? !authenticationType.equals(that.authenticationType) : that.authenticationType != null)
            return false;
        if (sId != null ? !sId.equals(that.sId) : that.sId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (merchantId != null ? merchantId.hashCode() : 0);
        result = 31 * result + (xid != null ? xid.hashCode() : 0);
        result = 31 * result + (mdStatus != null ? mdStatus.hashCode() : 0);
        result = 31 * result + (mdErrorMsg != null ? mdErrorMsg.hashCode() : 0);
        result = 31 * result + (veresEnrolledStatus != null ? veresEnrolledStatus.hashCode() : 0);
        result = 31 * result + (paresTxStatus != null ? paresTxStatus.hashCode() : 0);
        result = 31 * result + (iReqCode != null ? iReqCode.hashCode() : 0);
        result = 31 * result + (iReqDetail != null ? iReqDetail.hashCode() : 0);
        result = 31 * result + (vendorCode != null ? vendorCode.hashCode() : 0);
        result = 31 * result + (eci != null ? eci.hashCode() : 0);
        result = 31 * result + (cavv != null ? cavv.hashCode() : 0);
        result = 31 * result + (cavvAlgorithm != null ? cavvAlgorithm.hashCode() : 0);
        result = 31 * result + (md != null ? md.hashCode() : 0);
        result = 31 * result + (paResVerified != null ? paResVerified.hashCode() : 0);
        result = 31 * result + (paResSyntaxOk != null ? paResSyntaxOk.hashCode() : 0);
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        result = 31 * result + (cardType != null ? cardType.hashCode() : 0);
        result = 31 * result + (transStatus != null ? transStatus.hashCode() : 0);
        result = 31 * result + (dsTransId != null ? dsTransId.hashCode() : 0);
        result = 31 * result + (acsTransId != null ? acsTransId.hashCode() : 0);
        result = 31 * result + (acsReferenceNumber != null ? acsReferenceNumber.hashCode() : 0);
        result = 31 * result + (authTimestamp != null ? authTimestamp.hashCode() : 0);
        result = 31 * result + (authenticationType != null ? authenticationType.hashCode() : 0);
        result = 31 * result + (sId != null ? sId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "three_ds_transaction_id", referencedColumnName = "id", nullable = false)
    public ThreeDsTransactionEntity getThreeDsTransactionByThreeDsTransactionId() {
        return threeDsTransactionByThreeDsTransactionId;
    }

    public void setThreeDsTransactionByThreeDsTransactionId(ThreeDsTransactionEntity threeDsTransactionByThreeDsTransactionId) {
        this.threeDsTransactionByThreeDsTransactionId = threeDsTransactionByThreeDsTransactionId;
    }
}
