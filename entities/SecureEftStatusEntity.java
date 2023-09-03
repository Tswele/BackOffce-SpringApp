package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "secure_eft_status", schema = "dbo", catalog = "transaction_db")
public class SecureEftStatusEntity {
    private long id;
    private Long callpayId;
    private Integer successful;
    private String status;
    private BigDecimal amount;
    private String reason;
    private String currency;
    private String merchantReference;
    private String gatewayReference;
    private String paymentKey;
    private String created;
    private String displayAmount;
    private BigDecimal refundedAmount;
    private Integer refunded;
    private String service;
    private String gateway;
    private String customerAccount;
    private String customerAccountType;
    private String customerBank;
    private String customerBranchCode;
    private String call;
    private String location;
    private String signature;
    private Integer isDemoTransaction;
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
    @Column(name = "callpay_id", nullable = true)
    public Long getCallpayId() {
        return callpayId;
    }

    public void setCallpayId(Long callpayId) {
        this.callpayId = callpayId;
    }

    @Basic
    @Column(name = "successful", nullable = true)
    public Integer getSuccessful() {
        return successful;
    }

    public void setSuccessful(Integer successful) {
        this.successful = successful;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 100)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "amount", nullable = true, precision = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "reason", nullable = true, length = 100)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @Column(name = "currency", nullable = true, length = 100)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "merchant_reference", nullable = true, length = 100)
    public String getMerchantReference() {
        return merchantReference;
    }

    public void setMerchantReference(String merchantReference) {
        this.merchantReference = merchantReference;
    }

    @Basic
    @Column(name = "gateway_reference", nullable = true, length = 100)
    public String getGatewayReference() {
        return gatewayReference;
    }

    public void setGatewayReference(String gatewayReference) {
        this.gatewayReference = gatewayReference;
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
    @Column(name = "created", nullable = true, length = 100)
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Basic
    @Column(name = "display_amount", nullable = true, length = 100)
    public String getDisplayAmount() {
        return displayAmount;
    }

    public void setDisplayAmount(String displayAmount) {
        this.displayAmount = displayAmount;
    }

    @Basic
    @Column(name = "refunded_amount", nullable = true, precision = 2)
    public BigDecimal getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(BigDecimal refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    @Basic
    @Column(name = "refunded", nullable = true)
    public Integer getRefunded() {
        return refunded;
    }

    public void setRefunded(Integer refunded) {
        this.refunded = refunded;
    }

    @Basic
    @Column(name = "service", nullable = true, length = 100)
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Basic
    @Column(name = "gateway", nullable = true, length = 100)
    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    @Basic
    @Column(name = "customer_account", nullable = true, length = 100)
    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    @Basic
    @Column(name = "customer_account_type", nullable = true, length = 100)
    public String getCustomerAccountType() {
        return customerAccountType;
    }

    public void setCustomerAccountType(String customerAccountType) {
        this.customerAccountType = customerAccountType;
    }

    @Basic
    @Column(name = "customer_bank", nullable = true, length = 100)
    public String getCustomerBank() {
        return customerBank;
    }

    public void setCustomerBank(String customerBank) {
        this.customerBank = customerBank;
    }

    @Basic
    @Column(name = "customer_branch_code", nullable = true, length = 100)
    public String getCustomerBranchCode() {
        return customerBranchCode;
    }

    public void setCustomerBranchCode(String customerBranchCode) {
        this.customerBranchCode = customerBranchCode;
    }

    @Basic
    @Column(name = "call", nullable = true, length = 100)
    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    @Basic
    @Column(name = "location", nullable = true, length = 100)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "signature", nullable = true, length = 100)
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Basic
    @Column(name = "is_demo_transaction", nullable = true)
    public Integer getIsDemoTransaction() {
        return isDemoTransaction;
    }

    public void setIsDemoTransaction(Integer isDemoTransaction) {
        this.isDemoTransaction = isDemoTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecureEftStatusEntity that = (SecureEftStatusEntity) o;

        if (id != that.id) return false;
        if (callpayId != null ? !callpayId.equals(that.callpayId) : that.callpayId != null) return false;
        if (successful != null ? !successful.equals(that.successful) : that.successful != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (merchantReference != null ? !merchantReference.equals(that.merchantReference) : that.merchantReference != null)
            return false;
        if (gatewayReference != null ? !gatewayReference.equals(that.gatewayReference) : that.gatewayReference != null)
            return false;
        if (paymentKey != null ? !paymentKey.equals(that.paymentKey) : that.paymentKey != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (displayAmount != null ? !displayAmount.equals(that.displayAmount) : that.displayAmount != null)
            return false;
        if (refundedAmount != null ? !refundedAmount.equals(that.refundedAmount) : that.refundedAmount != null)
            return false;
        if (refunded != null ? !refunded.equals(that.refunded) : that.refunded != null) return false;
        if (service != null ? !service.equals(that.service) : that.service != null) return false;
        if (gateway != null ? !gateway.equals(that.gateway) : that.gateway != null) return false;
        if (customerAccount != null ? !customerAccount.equals(that.customerAccount) : that.customerAccount != null)
            return false;
        if (customerAccountType != null ? !customerAccountType.equals(that.customerAccountType) : that.customerAccountType != null)
            return false;
        if (customerBank != null ? !customerBank.equals(that.customerBank) : that.customerBank != null) return false;
        if (customerBranchCode != null ? !customerBranchCode.equals(that.customerBranchCode) : that.customerBranchCode != null)
            return false;
        if (call != null ? !call.equals(that.call) : that.call != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (signature != null ? !signature.equals(that.signature) : that.signature != null) return false;
        if (isDemoTransaction != null ? !isDemoTransaction.equals(that.isDemoTransaction) : that.isDemoTransaction != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (callpayId != null ? callpayId.hashCode() : 0);
        result = 31 * result + (successful != null ? successful.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (merchantReference != null ? merchantReference.hashCode() : 0);
        result = 31 * result + (gatewayReference != null ? gatewayReference.hashCode() : 0);
        result = 31 * result + (paymentKey != null ? paymentKey.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (displayAmount != null ? displayAmount.hashCode() : 0);
        result = 31 * result + (refundedAmount != null ? refundedAmount.hashCode() : 0);
        result = 31 * result + (refunded != null ? refunded.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (gateway != null ? gateway.hashCode() : 0);
        result = 31 * result + (customerAccount != null ? customerAccount.hashCode() : 0);
        result = 31 * result + (customerAccountType != null ? customerAccountType.hashCode() : 0);
        result = 31 * result + (customerBank != null ? customerBank.hashCode() : 0);
        result = 31 * result + (customerBranchCode != null ? customerBranchCode.hashCode() : 0);
        result = 31 * result + (call != null ? call.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        result = 31 * result + (isDemoTransaction != null ? isDemoTransaction.hashCode() : 0);
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
