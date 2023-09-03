package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "master_pass_tx", schema = "dbo", catalog = "transaction_db")
public class MasterPassTxEntity {
    private long id;
    private String callbackUrl;
    private BigDecimal amount;
    private String reference;
    private String description;
    private String cartItems;
    private Boolean requestShipping;
    private String rrn2;
    private String terminalId;
    private String subMerchantName;
    private Long mcc;
    private String responseReference;
    private String redirectUrl;
    private String status;
    private Timestamp lastModified;
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
    @Column(name = "callback_url", nullable = false, length = 400)
    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @Basic
    @Column(name = "amount", nullable = false, precision = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "reference", nullable = false, length = 45)
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 45)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "cart_items", nullable = true, length = 1024)
    public String getCartItems() {
        return cartItems;
    }

    public void setCartItems(String cartItems) {
        this.cartItems = cartItems;
    }

    @Basic
    @Column(name = "request_shipping", nullable = true)
    public Boolean getRequestShipping() {
        return requestShipping;
    }

    public void setRequestShipping(Boolean requestShipping) {
        this.requestShipping = requestShipping;
    }

    @Basic
    @Column(name = "rrn2", nullable = true, length = 12)
    public String getRrn2() {
        return rrn2;
    }

    public void setRrn2(String rrn2) {
        this.rrn2 = rrn2;
    }

    @Basic
    @Column(name = "terminal_id", nullable = true, length = 40)
    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    @Basic
    @Column(name = "sub_merchant_name", nullable = true, length = 40)
    public String getSubMerchantName() {
        return subMerchantName;
    }

    public void setSubMerchantName(String subMerchantName) {
        this.subMerchantName = subMerchantName;
    }

    @Basic
    @Column(name = "mcc", nullable = true)
    public Long getMcc() {
        return mcc;
    }

    public void setMcc(Long mcc) {
        this.mcc = mcc;
    }

    @Basic
    @Column(name = "response_reference", nullable = true, length = 1024)
    public String getResponseReference() {
        return responseReference;
    }

    public void setResponseReference(String responseReference) {
        this.responseReference = responseReference;
    }

    @Basic
    @Column(name = "redirect_url", nullable = true, length = 1024)
    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 1024)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "last_modified", nullable = false)
    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MasterPassTxEntity that = (MasterPassTxEntity) o;

        if (id != that.id) return false;
        if (callbackUrl != null ? !callbackUrl.equals(that.callbackUrl) : that.callbackUrl != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (reference != null ? !reference.equals(that.reference) : that.reference != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (cartItems != null ? !cartItems.equals(that.cartItems) : that.cartItems != null) return false;
        if (requestShipping != null ? !requestShipping.equals(that.requestShipping) : that.requestShipping != null)
            return false;
        if (rrn2 != null ? !rrn2.equals(that.rrn2) : that.rrn2 != null) return false;
        if (terminalId != null ? !terminalId.equals(that.terminalId) : that.terminalId != null) return false;
        if (subMerchantName != null ? !subMerchantName.equals(that.subMerchantName) : that.subMerchantName != null)
            return false;
        if (mcc != null ? !mcc.equals(that.mcc) : that.mcc != null) return false;
        if (responseReference != null ? !responseReference.equals(that.responseReference) : that.responseReference != null)
            return false;
        if (redirectUrl != null ? !redirectUrl.equals(that.redirectUrl) : that.redirectUrl != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (lastModified != null ? !lastModified.equals(that.lastModified) : that.lastModified != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (callbackUrl != null ? callbackUrl.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (cartItems != null ? cartItems.hashCode() : 0);
        result = 31 * result + (requestShipping != null ? requestShipping.hashCode() : 0);
        result = 31 * result + (rrn2 != null ? rrn2.hashCode() : 0);
        result = 31 * result + (terminalId != null ? terminalId.hashCode() : 0);
        result = 31 * result + (subMerchantName != null ? subMerchantName.hashCode() : 0);
        result = 31 * result + (mcc != null ? mcc.hashCode() : 0);
        result = 31 * result + (responseReference != null ? responseReference.hashCode() : 0);
        result = 31 * result + (redirectUrl != null ? redirectUrl.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
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
