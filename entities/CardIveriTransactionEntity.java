package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;

@Entity
@Table(name = "card_iveri_transaction", schema = "dbo", catalog = "transaction_db")
public class CardIveriTransactionEntity {
    private long id;
    private String version;
    private String certificateId;
    private String direction;
    private String ccNumber;
    private String expiryDate;
    private String cardSecurityCode;
    private String currency;
    private String terminal;
    private String amount;
    private String budgetPeriod;
    private String merchantReference;
    private String merchantTrace;
    private String electronicCommerceIndicator;
    private String cardHolderAuthenticationData;
    private String cardHolderAuthenticationId;
    private String applicationId;
    private String command;
    private String mode;
    private String validateRequest;
    private String protocol;
    private String protocolVersion;
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
    @Column(name = "version", nullable = true, length = 50)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "certificate_id", nullable = true, length = 2147483647)
    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    @Basic
    @Column(name = "direction", nullable = true, length = 50)
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Basic
    @Column(name = "cc_number", nullable = true, length = 2147483647)
    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    @Basic
    @Column(name = "expiry_date", nullable = true, length = 2147483647)
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Basic
    @Column(name = "card_security_code", nullable = true, length = 2147483647)
    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public void setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    @Basic
    @Column(name = "currency", nullable = true, length = 2147483647)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "terminal", nullable = true, length = 2147483647)
    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    @Basic
    @Column(name = "amount", nullable = true, length = 2147483647)
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "budget_period", nullable = true, length = 2147483647)
    public String getBudgetPeriod() {
        return budgetPeriod;
    }

    public void setBudgetPeriod(String budgetPeriod) {
        this.budgetPeriod = budgetPeriod;
    }

    @Basic
    @Column(name = "merchant_reference", nullable = true, length = 2147483647)
    public String getMerchantReference() {
        return merchantReference;
    }

    public void setMerchantReference(String merchantReference) {
        this.merchantReference = merchantReference;
    }

    @Basic
    @Column(name = "merchant_trace", nullable = true, length = 2147483647)
    public String getMerchantTrace() {
        return merchantTrace;
    }

    public void setMerchantTrace(String merchantTrace) {
        this.merchantTrace = merchantTrace;
    }

    @Basic
    @Column(name = "electronic_commerce_indicator", nullable = true, length = 2147483647)
    public String getElectronicCommerceIndicator() {
        return electronicCommerceIndicator;
    }

    public void setElectronicCommerceIndicator(String electronicCommerceIndicator) {
        this.electronicCommerceIndicator = electronicCommerceIndicator;
    }

    @Basic
    @Column(name = "card_holder_authentication_data", nullable = true, length = 2147483647)
    public String getCardHolderAuthenticationData() {
        return cardHolderAuthenticationData;
    }

    public void setCardHolderAuthenticationData(String cardHolderAuthenticationData) {
        this.cardHolderAuthenticationData = cardHolderAuthenticationData;
    }

    @Basic
    @Column(name = "card_holder_authentication_id", nullable = true, length = 2147483647)
    public String getCardHolderAuthenticationId() {
        return cardHolderAuthenticationId;
    }

    public void setCardHolderAuthenticationId(String cardHolderAuthenticationId) {
        this.cardHolderAuthenticationId = cardHolderAuthenticationId;
    }

    @Basic
    @Column(name = "application_id", nullable = true, length = 2147483647)
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @Basic
    @Column(name = "command", nullable = true, length = 50)
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Basic
    @Column(name = "mode", nullable = true, length = 50)
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Basic
    @Column(name = "validate_request", nullable = true, length = 50)
    public String getValidateRequest() {
        return validateRequest;
    }

    public void setValidateRequest(String validateRequest) {
        this.validateRequest = validateRequest;
    }

    @Basic
    @Column(name = "protocol", nullable = true, length = 50)
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Basic
    @Column(name = "protocol_version", nullable = true, length = 50)
    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardIveriTransactionEntity that = (CardIveriTransactionEntity) o;

        if (id != that.id) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (certificateId != null ? !certificateId.equals(that.certificateId) : that.certificateId != null)
            return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
        if (ccNumber != null ? !ccNumber.equals(that.ccNumber) : that.ccNumber != null) return false;
        if (expiryDate != null ? !expiryDate.equals(that.expiryDate) : that.expiryDate != null) return false;
        if (cardSecurityCode != null ? !cardSecurityCode.equals(that.cardSecurityCode) : that.cardSecurityCode != null)
            return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (terminal != null ? !terminal.equals(that.terminal) : that.terminal != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (budgetPeriod != null ? !budgetPeriod.equals(that.budgetPeriod) : that.budgetPeriod != null) return false;
        if (merchantReference != null ? !merchantReference.equals(that.merchantReference) : that.merchantReference != null)
            return false;
        if (merchantTrace != null ? !merchantTrace.equals(that.merchantTrace) : that.merchantTrace != null)
            return false;
        if (electronicCommerceIndicator != null ? !electronicCommerceIndicator.equals(that.electronicCommerceIndicator) : that.electronicCommerceIndicator != null)
            return false;
        if (cardHolderAuthenticationData != null ? !cardHolderAuthenticationData.equals(that.cardHolderAuthenticationData) : that.cardHolderAuthenticationData != null)
            return false;
        if (cardHolderAuthenticationId != null ? !cardHolderAuthenticationId.equals(that.cardHolderAuthenticationId) : that.cardHolderAuthenticationId != null)
            return false;
        if (applicationId != null ? !applicationId.equals(that.applicationId) : that.applicationId != null)
            return false;
        if (command != null ? !command.equals(that.command) : that.command != null) return false;
        if (mode != null ? !mode.equals(that.mode) : that.mode != null) return false;
        if (validateRequest != null ? !validateRequest.equals(that.validateRequest) : that.validateRequest != null)
            return false;
        if (protocol != null ? !protocol.equals(that.protocol) : that.protocol != null) return false;
        if (protocolVersion != null ? !protocolVersion.equals(that.protocolVersion) : that.protocolVersion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (certificateId != null ? certificateId.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (ccNumber != null ? ccNumber.hashCode() : 0);
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        result = 31 * result + (cardSecurityCode != null ? cardSecurityCode.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (terminal != null ? terminal.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (budgetPeriod != null ? budgetPeriod.hashCode() : 0);
        result = 31 * result + (merchantReference != null ? merchantReference.hashCode() : 0);
        result = 31 * result + (merchantTrace != null ? merchantTrace.hashCode() : 0);
        result = 31 * result + (electronicCommerceIndicator != null ? electronicCommerceIndicator.hashCode() : 0);
        result = 31 * result + (cardHolderAuthenticationData != null ? cardHolderAuthenticationData.hashCode() : 0);
        result = 31 * result + (cardHolderAuthenticationId != null ? cardHolderAuthenticationId.hashCode() : 0);
        result = 31 * result + (applicationId != null ? applicationId.hashCode() : 0);
        result = 31 * result + (command != null ? command.hashCode() : 0);
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        result = 31 * result + (validateRequest != null ? validateRequest.hashCode() : 0);
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        result = 31 * result + (protocolVersion != null ? protocolVersion.hashCode() : 0);
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
