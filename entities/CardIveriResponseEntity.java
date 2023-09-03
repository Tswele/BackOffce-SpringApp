package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;

@Entity
@Table(name = "card_iveri_response", schema = "dbo", catalog = "transaction_db")
public class CardIveriResponseEntity {
    private long id;
    private String version;
    private String direction;
    private String applicationId;
    private String command;
    private String mode;
    private String requestId;
    private String merchantTrace;
    private String amount;
    private String authorisationCode;
    private String ccNumber;
    private String currency;
    private String electronicCommerceIndicator;
    private String expiryDate;
    private String merchantReference;
    private String terminal;
    private String transactionIndex;
    private String originalRequestId;
    private String merchantName;
    private String merchantUsn;
    private String acquirer;
    private String acquirerReference;
    private String acquirerDate;
    private String acquirerTime;
    private String displayAmount;
    private String bin;
    private String association;
    private String cardType;
    private String issuer;
    private String jurisdiction;
    private String pan;
    private String panMode;
    private String reconReference;
    private String cardHolderPresence;
    private String merchantAddress;
    private String merchantCity;
    private String merchantCountryCode;
    private String merchantCountry;
    private String distributorName;
    private CardIveriResponseEntity cardIveriResponseById;
    private CardIveriResponseEntity cardIveriResponseById_0;
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
    @Column(name = "direction", nullable = true, length = 50)
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
    @Column(name = "command", nullable = true, length = 2147483647)
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Basic
    @Column(name = "mode", nullable = true, length = 2147483647)
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Basic
    @Column(name = "request_id", nullable = true, length = 2147483647)
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
    @Column(name = "amount", nullable = true, length = 2147483647)
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "authorisation_code", nullable = true, length = 2147483647)
    public String getAuthorisationCode() {
        return authorisationCode;
    }

    public void setAuthorisationCode(String authorisationCode) {
        this.authorisationCode = authorisationCode;
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
    @Column(name = "currency", nullable = true, length = 2147483647)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
    @Column(name = "expiry_date", nullable = true, length = 2147483647)
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
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
    @Column(name = "terminal", nullable = true, length = 2147483647)
    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    @Basic
    @Column(name = "transaction_index", nullable = true, length = 2147483647)
    public String getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    @Basic
    @Column(name = "original_request_id", nullable = true, length = 2147483647)
    public String getOriginalRequestId() {
        return originalRequestId;
    }

    public void setOriginalRequestId(String originalRequestId) {
        this.originalRequestId = originalRequestId;
    }

    @Basic
    @Column(name = "merchant_name", nullable = true, length = 2147483647)
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Basic
    @Column(name = "merchant_usn", nullable = true, length = 2147483647)
    public String getMerchantUsn() {
        return merchantUsn;
    }

    public void setMerchantUsn(String merchantUsn) {
        this.merchantUsn = merchantUsn;
    }

    @Basic
    @Column(name = "acquirer", nullable = true, length = 2147483647)
    public String getAcquirer() {
        return acquirer;
    }

    public void setAcquirer(String acquirer) {
        this.acquirer = acquirer;
    }

    @Basic
    @Column(name = "acquirer_reference", nullable = true, length = 2147483647)
    public String getAcquirerReference() {
        return acquirerReference;
    }

    public void setAcquirerReference(String acquirerReference) {
        this.acquirerReference = acquirerReference;
    }

    @Basic
    @Column(name = "acquirer_date", nullable = true, length = 2147483647)
    public String getAcquirerDate() {
        return acquirerDate;
    }

    public void setAcquirerDate(String acquirerDate) {
        this.acquirerDate = acquirerDate;
    }

    @Basic
    @Column(name = "acquirer_time", nullable = true, length = 2147483647)
    public String getAcquirerTime() {
        return acquirerTime;
    }

    public void setAcquirerTime(String acquirerTime) {
        this.acquirerTime = acquirerTime;
    }

    @Basic
    @Column(name = "display_amount", nullable = true, length = 2147483647)
    public String getDisplayAmount() {
        return displayAmount;
    }

    public void setDisplayAmount(String displayAmount) {
        this.displayAmount = displayAmount;
    }

    @Basic
    @Column(name = "bin", nullable = true, length = 2147483647)
    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    @Basic
    @Column(name = "association", nullable = true, length = 2147483647)
    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
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
    @Column(name = "issuer", nullable = true, length = 2147483647)
    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    @Basic
    @Column(name = "jurisdiction", nullable = true, length = 2147483647)
    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    @Basic
    @Column(name = "pan", nullable = true, length = 2147483647)
    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    @Basic
    @Column(name = "pan_mode", nullable = true, length = 2147483647)
    public String getPanMode() {
        return panMode;
    }

    public void setPanMode(String panMode) {
        this.panMode = panMode;
    }

    @Basic
    @Column(name = "recon_reference", nullable = true, length = 2147483647)
    public String getReconReference() {
        return reconReference;
    }

    public void setReconReference(String reconReference) {
        this.reconReference = reconReference;
    }

    @Basic
    @Column(name = "card_holder_presence", nullable = true, length = 2147483647)
    public String getCardHolderPresence() {
        return cardHolderPresence;
    }

    public void setCardHolderPresence(String cardHolderPresence) {
        this.cardHolderPresence = cardHolderPresence;
    }

    @Basic
    @Column(name = "merchant_address", nullable = true, length = 2147483647)
    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    @Basic
    @Column(name = "merchant_city", nullable = true, length = 2147483647)
    public String getMerchantCity() {
        return merchantCity;
    }

    public void setMerchantCity(String merchantCity) {
        this.merchantCity = merchantCity;
    }

    @Basic
    @Column(name = "merchant_country_code", nullable = true, length = 2147483647)
    public String getMerchantCountryCode() {
        return merchantCountryCode;
    }

    public void setMerchantCountryCode(String merchantCountryCode) {
        this.merchantCountryCode = merchantCountryCode;
    }

    @Basic
    @Column(name = "merchant_country", nullable = true, length = 2147483647)
    public String getMerchantCountry() {
        return merchantCountry;
    }

    public void setMerchantCountry(String merchantCountry) {
        this.merchantCountry = merchantCountry;
    }

    @Basic
    @Column(name = "distributor_name", nullable = true, length = 2147483647)
    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardIveriResponseEntity that = (CardIveriResponseEntity) o;

        if (id != that.id) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
        if (applicationId != null ? !applicationId.equals(that.applicationId) : that.applicationId != null)
            return false;
        if (command != null ? !command.equals(that.command) : that.command != null) return false;
        if (mode != null ? !mode.equals(that.mode) : that.mode != null) return false;
        if (requestId != null ? !requestId.equals(that.requestId) : that.requestId != null) return false;
        if (merchantTrace != null ? !merchantTrace.equals(that.merchantTrace) : that.merchantTrace != null)
            return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (authorisationCode != null ? !authorisationCode.equals(that.authorisationCode) : that.authorisationCode != null)
            return false;
        if (ccNumber != null ? !ccNumber.equals(that.ccNumber) : that.ccNumber != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (electronicCommerceIndicator != null ? !electronicCommerceIndicator.equals(that.electronicCommerceIndicator) : that.electronicCommerceIndicator != null)
            return false;
        if (expiryDate != null ? !expiryDate.equals(that.expiryDate) : that.expiryDate != null) return false;
        if (merchantReference != null ? !merchantReference.equals(that.merchantReference) : that.merchantReference != null)
            return false;
        if (terminal != null ? !terminal.equals(that.terminal) : that.terminal != null) return false;
        if (transactionIndex != null ? !transactionIndex.equals(that.transactionIndex) : that.transactionIndex != null)
            return false;
        if (originalRequestId != null ? !originalRequestId.equals(that.originalRequestId) : that.originalRequestId != null)
            return false;
        if (merchantName != null ? !merchantName.equals(that.merchantName) : that.merchantName != null) return false;
        if (merchantUsn != null ? !merchantUsn.equals(that.merchantUsn) : that.merchantUsn != null) return false;
        if (acquirer != null ? !acquirer.equals(that.acquirer) : that.acquirer != null) return false;
        if (acquirerReference != null ? !acquirerReference.equals(that.acquirerReference) : that.acquirerReference != null)
            return false;
        if (acquirerDate != null ? !acquirerDate.equals(that.acquirerDate) : that.acquirerDate != null) return false;
        if (acquirerTime != null ? !acquirerTime.equals(that.acquirerTime) : that.acquirerTime != null) return false;
        if (displayAmount != null ? !displayAmount.equals(that.displayAmount) : that.displayAmount != null)
            return false;
        if (bin != null ? !bin.equals(that.bin) : that.bin != null) return false;
        if (association != null ? !association.equals(that.association) : that.association != null) return false;
        if (cardType != null ? !cardType.equals(that.cardType) : that.cardType != null) return false;
        if (issuer != null ? !issuer.equals(that.issuer) : that.issuer != null) return false;
        if (jurisdiction != null ? !jurisdiction.equals(that.jurisdiction) : that.jurisdiction != null) return false;
        if (pan != null ? !pan.equals(that.pan) : that.pan != null) return false;
        if (panMode != null ? !panMode.equals(that.panMode) : that.panMode != null) return false;
        if (reconReference != null ? !reconReference.equals(that.reconReference) : that.reconReference != null)
            return false;
        if (cardHolderPresence != null ? !cardHolderPresence.equals(that.cardHolderPresence) : that.cardHolderPresence != null)
            return false;
        if (merchantAddress != null ? !merchantAddress.equals(that.merchantAddress) : that.merchantAddress != null)
            return false;
        if (merchantCity != null ? !merchantCity.equals(that.merchantCity) : that.merchantCity != null) return false;
        if (merchantCountryCode != null ? !merchantCountryCode.equals(that.merchantCountryCode) : that.merchantCountryCode != null)
            return false;
        if (merchantCountry != null ? !merchantCountry.equals(that.merchantCountry) : that.merchantCountry != null)
            return false;
        if (distributorName != null ? !distributorName.equals(that.distributorName) : that.distributorName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (applicationId != null ? applicationId.hashCode() : 0);
        result = 31 * result + (command != null ? command.hashCode() : 0);
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        result = 31 * result + (requestId != null ? requestId.hashCode() : 0);
        result = 31 * result + (merchantTrace != null ? merchantTrace.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (authorisationCode != null ? authorisationCode.hashCode() : 0);
        result = 31 * result + (ccNumber != null ? ccNumber.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (electronicCommerceIndicator != null ? electronicCommerceIndicator.hashCode() : 0);
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        result = 31 * result + (merchantReference != null ? merchantReference.hashCode() : 0);
        result = 31 * result + (terminal != null ? terminal.hashCode() : 0);
        result = 31 * result + (transactionIndex != null ? transactionIndex.hashCode() : 0);
        result = 31 * result + (originalRequestId != null ? originalRequestId.hashCode() : 0);
        result = 31 * result + (merchantName != null ? merchantName.hashCode() : 0);
        result = 31 * result + (merchantUsn != null ? merchantUsn.hashCode() : 0);
        result = 31 * result + (acquirer != null ? acquirer.hashCode() : 0);
        result = 31 * result + (acquirerReference != null ? acquirerReference.hashCode() : 0);
        result = 31 * result + (acquirerDate != null ? acquirerDate.hashCode() : 0);
        result = 31 * result + (acquirerTime != null ? acquirerTime.hashCode() : 0);
        result = 31 * result + (displayAmount != null ? displayAmount.hashCode() : 0);
        result = 31 * result + (bin != null ? bin.hashCode() : 0);
        result = 31 * result + (association != null ? association.hashCode() : 0);
        result = 31 * result + (cardType != null ? cardType.hashCode() : 0);
        result = 31 * result + (issuer != null ? issuer.hashCode() : 0);
        result = 31 * result + (jurisdiction != null ? jurisdiction.hashCode() : 0);
        result = 31 * result + (pan != null ? pan.hashCode() : 0);
        result = 31 * result + (panMode != null ? panMode.hashCode() : 0);
        result = 31 * result + (reconReference != null ? reconReference.hashCode() : 0);
        result = 31 * result + (cardHolderPresence != null ? cardHolderPresence.hashCode() : 0);
        result = 31 * result + (merchantAddress != null ? merchantAddress.hashCode() : 0);
        result = 31 * result + (merchantCity != null ? merchantCity.hashCode() : 0);
        result = 31 * result + (merchantCountryCode != null ? merchantCountryCode.hashCode() : 0);
        result = 31 * result + (merchantCountry != null ? merchantCountry.hashCode() : 0);
        result = 31 * result + (distributorName != null ? distributorName.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    public CardIveriResponseEntity getCardIveriResponseById() {
        return cardIveriResponseById;
    }

    public void setCardIveriResponseById(CardIveriResponseEntity cardIveriResponseById) {
        this.cardIveriResponseById = cardIveriResponseById;
    }

    @OneToOne(mappedBy = "cardIveriResponseById")
    public CardIveriResponseEntity getCardIveriResponseById_0() {
        return cardIveriResponseById_0;
    }

    public void setCardIveriResponseById_0(CardIveriResponseEntity cardIveriResponseById_0) {
        this.cardIveriResponseById_0 = cardIveriResponseById_0;
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
