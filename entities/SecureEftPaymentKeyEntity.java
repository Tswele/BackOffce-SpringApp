package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;

@Entity
@Table(name = "secure_eft_payment_key", schema = "dbo", catalog = "transaction_db")
public class SecureEftPaymentKeyEntity {
    private long id;
    private String paymentKey;
    private String url;
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
    @Column(name = "payment_key", nullable = true, length = 100)
    public String getPaymentKey() {
        return paymentKey;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    @Basic
    @Column(name = "url", nullable = true, length = 200)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecureEftPaymentKeyEntity that = (SecureEftPaymentKeyEntity) o;

        if (id != that.id) return false;
        if (paymentKey != null ? !paymentKey.equals(that.paymentKey) : that.paymentKey != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (paymentKey != null ? paymentKey.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
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
