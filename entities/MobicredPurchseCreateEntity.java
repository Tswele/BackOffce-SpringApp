package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "mobicred_purchse_create", schema = "dbo", catalog = "transaction_db")
public class MobicredPurchseCreateEntity {
    private BigDecimal amount;
    private String paymentCreationMerchantReference;
    private String paymentDateTime;
    private Long paymentInstructionResponseCode;
    private String paymentCreationStatus;
    private String paymentCreationReason;
    private String paymentCreationReceivingCustomerMessage;
    private long id;
    private String mobicredRequestUid;
    private TransactionLegEntity transactionLegByTransactionLegId;

    @Basic
    @Column(name = "amount", nullable = true, precision = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "payment_creation_merchant_reference", nullable = true, length = 100)
    public String getPaymentCreationMerchantReference() {
        return paymentCreationMerchantReference;
    }

    public void setPaymentCreationMerchantReference(String paymentCreationMerchantReference) {
        this.paymentCreationMerchantReference = paymentCreationMerchantReference;
    }

    @Basic
    @Column(name = "payment_date_time", nullable = true, length = 100)
    public String getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(String paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    @Basic
    @Column(name = "payment_instruction_response_code", nullable = true)
    public Long getPaymentInstructionResponseCode() {
        return paymentInstructionResponseCode;
    }

    public void setPaymentInstructionResponseCode(Long paymentInstructionResponseCode) {
        this.paymentInstructionResponseCode = paymentInstructionResponseCode;
    }

    @Basic
    @Column(name = "payment_creation_status", nullable = true, length = 100)
    public String getPaymentCreationStatus() {
        return paymentCreationStatus;
    }

    public void setPaymentCreationStatus(String paymentCreationStatus) {
        this.paymentCreationStatus = paymentCreationStatus;
    }

    @Basic
    @Column(name = "payment_creation_reason", nullable = true, length = 100)
    public String getPaymentCreationReason() {
        return paymentCreationReason;
    }

    public void setPaymentCreationReason(String paymentCreationReason) {
        this.paymentCreationReason = paymentCreationReason;
    }

    @Basic
    @Column(name = "payment_creation_receiving_customer_message", nullable = true, length = 100)
    public String getPaymentCreationReceivingCustomerMessage() {
        return paymentCreationReceivingCustomerMessage;
    }

    public void setPaymentCreationReceivingCustomerMessage(String paymentCreationReceivingCustomerMessage) {
        this.paymentCreationReceivingCustomerMessage = paymentCreationReceivingCustomerMessage;
    }

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "mobicred_request_uid", nullable = true, length = 100)
    public String getMobicredRequestUid() {
        return mobicredRequestUid;
    }

    public void setMobicredRequestUid(String mobicredRequestUid) {
        this.mobicredRequestUid = mobicredRequestUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MobicredPurchseCreateEntity that = (MobicredPurchseCreateEntity) o;

        if (id != that.id) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (paymentCreationMerchantReference != null ? !paymentCreationMerchantReference.equals(that.paymentCreationMerchantReference) : that.paymentCreationMerchantReference != null)
            return false;
        if (paymentDateTime != null ? !paymentDateTime.equals(that.paymentDateTime) : that.paymentDateTime != null)
            return false;
        if (paymentInstructionResponseCode != null ? !paymentInstructionResponseCode.equals(that.paymentInstructionResponseCode) : that.paymentInstructionResponseCode != null)
            return false;
        if (paymentCreationStatus != null ? !paymentCreationStatus.equals(that.paymentCreationStatus) : that.paymentCreationStatus != null)
            return false;
        if (paymentCreationReason != null ? !paymentCreationReason.equals(that.paymentCreationReason) : that.paymentCreationReason != null)
            return false;
        if (paymentCreationReceivingCustomerMessage != null ? !paymentCreationReceivingCustomerMessage.equals(that.paymentCreationReceivingCustomerMessage) : that.paymentCreationReceivingCustomerMessage != null)
            return false;
        if (mobicredRequestUid != null ? !mobicredRequestUid.equals(that.mobicredRequestUid) : that.mobicredRequestUid != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (paymentCreationMerchantReference != null ? paymentCreationMerchantReference.hashCode() : 0);
        result = 31 * result + (paymentDateTime != null ? paymentDateTime.hashCode() : 0);
        result = 31 * result + (paymentInstructionResponseCode != null ? paymentInstructionResponseCode.hashCode() : 0);
        result = 31 * result + (paymentCreationStatus != null ? paymentCreationStatus.hashCode() : 0);
        result = 31 * result + (paymentCreationReason != null ? paymentCreationReason.hashCode() : 0);
        result = 31 * result + (paymentCreationReceivingCustomerMessage != null ? paymentCreationReceivingCustomerMessage.hashCode() : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (mobicredRequestUid != null ? mobicredRequestUid.hashCode() : 0);
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
