package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "card_postillion_transaction", schema = "dbo", catalog = "transaction_db")
public class CardPostillionTransactionEntity {
    private long id;
    private String tokendataQkMgTrxIdx;
    private Long transactionId;
    private String systemsTraceAuditNum;
    private String retrievalReferenceNum;
    private String processingCodeTransactionType;
    private String transactionType;
    private String processingCodeAccountTypeFrom;
    private String processingCodeAccountTypeTo;
    private String pan;
    private String purchaseTime;
    private String purchaseDate;
    private Long amount;
    private String expiryDate;
    private String posEntryModePanEntryMode;
    private String posConditionCode;
    private String acquiringInstitutionIdCode;
    private String cardAcceptorTerminalId;
    private String cardAcceptorIdCode;
    private String cardAcceptorName;
    private String cardAcceptorCity;
    private String cardAcceptorState;
    private String cardAcceptorCountry;
    private String additionalDataRetailerId;
    private String currencyCode;
    private String cardholderAuthenticationid;
    private String cardholderAuthenticationdata;
    private String budget;
    private String posEntryModePinCapability;
    private TransactionLegEntity transactionLegByTransactionLegId;
    private Timestamp dateLogged;
    private String structuredData;
    private String transmissionDatetime;
    private String authIdResponse;
    private String resultCode;
    private String acquiringInstitutionIdLength;
    private String originalDataElementsTransactionType;
    private String originalSystemsTraceAuditNumber;
    private String originalTransmissionDateAndTime;
    private String originalAcquirerInstitutionIdCode;
    private String originalForwardingInstitutionIdCode;
    private String merchantType;
    private String extendedPaymentCode;
    private String cardholderAuthenticationCapability;
    private String cardholderAuthenticationMethod;
    private String cardholderAuthenticationEntity;
    private String cardDataInputCapabilityPosition1;
    private String cardCaptureCapability;
    private String operatingEnvironment;
    private String cardholderIsPresent;
    private String cardIsPresent;
    private String cardDataInputCapabilityPosition7;
    private String cardDataOutputCapability;
    private String terminalOutputCapability;
    private String pinCaptureCapability;
    private String terminalOperator;
    private String authorizationProfile;
    private String terminalType;
    private String cardVerificationResult;
    private String ucafCollection;
    private String ucafAuthentication;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "tokendata_qk_mg_trx_idx", nullable = true, length = 2147483647)
    public String getTokendataQkMgTrxIdx() {
        return tokendataQkMgTrxIdx;
    }

    public void setTokendataQkMgTrxIdx(String tokendataQkMgTrxIdx) {
        this.tokendataQkMgTrxIdx = tokendataQkMgTrxIdx;
    }

    @Basic
    @Column(name = "transaction_id", nullable = true)
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "systems_trace_audit_num", nullable = true, length = 2147483647)
    public String getSystemsTraceAuditNum() {
        return systemsTraceAuditNum;
    }

    public void setSystemsTraceAuditNum(String systemsTraceAuditNum) {
        this.systemsTraceAuditNum = systemsTraceAuditNum;
    }

    @Basic
    @Column(name = "retrieval_reference_num", nullable = true, length = 2147483647)
    public String getRetrievalReferenceNum() {
        return retrievalReferenceNum;
    }

    public void setRetrievalReferenceNum(String retrievalReferenceNum) {
        this.retrievalReferenceNum = retrievalReferenceNum;
    }

    @Basic
    @Column(name = "processing_code_transaction_type", nullable = true, length = 2147483647)
    public String getProcessingCodeTransactionType() {
        return processingCodeTransactionType;
    }

    public void setProcessingCodeTransactionType(String processingCodeTransactionType) {
        this.processingCodeTransactionType = processingCodeTransactionType;
    }

    @Basic
    @Column(name = "transaction_type", nullable = true, length = 2147483647)
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Basic
    @Column(name = "processing_code_account_type_from", nullable = true, length = 2147483647)
    public String getProcessingCodeAccountTypeFrom() {
        return processingCodeAccountTypeFrom;
    }

    public void setProcessingCodeAccountTypeFrom(String processingCodeAccountTypeFrom) {
        this.processingCodeAccountTypeFrom = processingCodeAccountTypeFrom;
    }

    @Basic
    @Column(name = "processing_code_account_type_to", nullable = true, length = 2147483647)
    public String getProcessingCodeAccountTypeTo() {
        return processingCodeAccountTypeTo;
    }

    public void setProcessingCodeAccountTypeTo(String processingCodeAccountTypeTo) {
        this.processingCodeAccountTypeTo = processingCodeAccountTypeTo;
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
    @Column(name = "purchase_time", nullable = true, length = 2147483647)
    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Basic
    @Column(name = "purchase_date", nullable = true, length = 2147483647)
    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Basic
    @Column(name = "amount", nullable = true)
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
    @Column(name = "pos_entry_mode_pan_entry_mode", nullable = true, length = 2147483647)
    public String getPosEntryModePanEntryMode() {
        return posEntryModePanEntryMode;
    }

    public void setPosEntryModePanEntryMode(String posEntryModePanEntryMode) {
        this.posEntryModePanEntryMode = posEntryModePanEntryMode;
    }

    @Basic
    @Column(name = "pos_condition_code", nullable = true, length = 2147483647)
    public String getPosConditionCode() {
        return posConditionCode;
    }

    public void setPosConditionCode(String posConditionCode) {
        this.posConditionCode = posConditionCode;
    }

    @Basic
    @Column(name = "acquiring_institution_id_code", nullable = true, length = 2147483647)
    public String getAcquiringInstitutionIdCode() {
        return acquiringInstitutionIdCode;
    }

    public void setAcquiringInstitutionIdCode(String acquiringInstitutionIdCode) {
        this.acquiringInstitutionIdCode = acquiringInstitutionIdCode;
    }

    @Basic
    @Column(name = "card_acceptor_terminal_id", nullable = true, length = 2147483647)
    public String getCardAcceptorTerminalId() {
        return cardAcceptorTerminalId;
    }

    public void setCardAcceptorTerminalId(String cardAcceptorTerminalId) {
        this.cardAcceptorTerminalId = cardAcceptorTerminalId;
    }

    @Basic
    @Column(name = "card_acceptor_id_code", nullable = true, length = 2147483647)
    public String getCardAcceptorIdCode() {
        return cardAcceptorIdCode;
    }

    public void setCardAcceptorIdCode(String cardAcceptorIdCode) {
        this.cardAcceptorIdCode = cardAcceptorIdCode;
    }

    @Basic
    @Column(name = "card_acceptor_name", nullable = true, length = 2147483647)
    public String getCardAcceptorName() {
        return cardAcceptorName;
    }

    public void setCardAcceptorName(String cardAcceptorName) {
        this.cardAcceptorName = cardAcceptorName;
    }

    @Basic
    @Column(name = "card_acceptor_city", nullable = true, length = 2147483647)
    public String getCardAcceptorCity() {
        return cardAcceptorCity;
    }

    public void setCardAcceptorCity(String cardAcceptorCity) {
        this.cardAcceptorCity = cardAcceptorCity;
    }

    @Basic
    @Column(name = "card_acceptor_state", nullable = true, length = 2147483647)
    public String getCardAcceptorState() {
        return cardAcceptorState;
    }

    public void setCardAcceptorState(String cardAcceptorState) {
        this.cardAcceptorState = cardAcceptorState;
    }

    @Basic
    @Column(name = "card_acceptor_country", nullable = true, length = 2147483647)
    public String getCardAcceptorCountry() {
        return cardAcceptorCountry;
    }

    public void setCardAcceptorCountry(String cardAcceptorCountry) {
        this.cardAcceptorCountry = cardAcceptorCountry;
    }

    @Basic
    @Column(name = "additional_data_retailer_id", nullable = true, length = 2147483647)
    public String getAdditionalDataRetailerId() {
        return additionalDataRetailerId;
    }

    public void setAdditionalDataRetailerId(String additionalDataRetailerId) {
        this.additionalDataRetailerId = additionalDataRetailerId;
    }

    @Basic
    @Column(name = "currency_code", nullable = true, length = 2147483647)
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Basic
    @Column(name = "cardholder_authenticationid", nullable = true, length = 2147483647)
    public String getCardholderAuthenticationid() {
        return cardholderAuthenticationid;
    }

    public void setCardholderAuthenticationid(String cardholderAuthenticationid) {
        this.cardholderAuthenticationid = cardholderAuthenticationid;
    }

    @Basic
    @Column(name = "cardholder_authenticationdata", nullable = true, length = 2147483647)
    public String getCardholderAuthenticationdata() {
        return cardholderAuthenticationdata;
    }

    public void setCardholderAuthenticationdata(String cardholderAuthenticationdata) {
        this.cardholderAuthenticationdata = cardholderAuthenticationdata;
    }

    @Basic
    @Column(name = "budget", nullable = true, length = 2147483647)
    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    @Basic
    @Column(name = "pos_entry_mode_pin_capability", nullable = true, length = 2147483647)
    public String getPosEntryModePinCapability() {
        return posEntryModePinCapability;
    }

    public void setPosEntryModePinCapability(String posEntryModePinCapability) {
        this.posEntryModePinCapability = posEntryModePinCapability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardPostillionTransactionEntity that = (CardPostillionTransactionEntity) o;

        if (id != that.id) return false;
        if (tokendataQkMgTrxIdx != null ? !tokendataQkMgTrxIdx.equals(that.tokendataQkMgTrxIdx) : that.tokendataQkMgTrxIdx != null)
            return false;
        if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
            return false;
        if (systemsTraceAuditNum != null ? !systemsTraceAuditNum.equals(that.systemsTraceAuditNum) : that.systemsTraceAuditNum != null)
            return false;
        if (retrievalReferenceNum != null ? !retrievalReferenceNum.equals(that.retrievalReferenceNum) : that.retrievalReferenceNum != null)
            return false;
        if (processingCodeTransactionType != null ? !processingCodeTransactionType.equals(that.processingCodeTransactionType) : that.processingCodeTransactionType != null)
            return false;
        if (transactionType != null ? !transactionType.equals(that.transactionType) : that.transactionType != null)
            return false;
        if (processingCodeAccountTypeFrom != null ? !processingCodeAccountTypeFrom.equals(that.processingCodeAccountTypeFrom) : that.processingCodeAccountTypeFrom != null)
            return false;
        if (processingCodeAccountTypeTo != null ? !processingCodeAccountTypeTo.equals(that.processingCodeAccountTypeTo) : that.processingCodeAccountTypeTo != null)
            return false;
        if (pan != null ? !pan.equals(that.pan) : that.pan != null) return false;
        if (purchaseTime != null ? !purchaseTime.equals(that.purchaseTime) : that.purchaseTime != null) return false;
        if (purchaseDate != null ? !purchaseDate.equals(that.purchaseDate) : that.purchaseDate != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (expiryDate != null ? !expiryDate.equals(that.expiryDate) : that.expiryDate != null) return false;
        if (posEntryModePanEntryMode != null ? !posEntryModePanEntryMode.equals(that.posEntryModePanEntryMode) : that.posEntryModePanEntryMode != null)
            return false;
        if (posConditionCode != null ? !posConditionCode.equals(that.posConditionCode) : that.posConditionCode != null)
            return false;
        if (acquiringInstitutionIdCode != null ? !acquiringInstitutionIdCode.equals(that.acquiringInstitutionIdCode) : that.acquiringInstitutionIdCode != null)
            return false;
        if (cardAcceptorTerminalId != null ? !cardAcceptorTerminalId.equals(that.cardAcceptorTerminalId) : that.cardAcceptorTerminalId != null)
            return false;
        if (cardAcceptorIdCode != null ? !cardAcceptorIdCode.equals(that.cardAcceptorIdCode) : that.cardAcceptorIdCode != null)
            return false;
        if (cardAcceptorName != null ? !cardAcceptorName.equals(that.cardAcceptorName) : that.cardAcceptorName != null)
            return false;
        if (cardAcceptorCity != null ? !cardAcceptorCity.equals(that.cardAcceptorCity) : that.cardAcceptorCity != null)
            return false;
        if (cardAcceptorState != null ? !cardAcceptorState.equals(that.cardAcceptorState) : that.cardAcceptorState != null)
            return false;
        if (cardAcceptorCountry != null ? !cardAcceptorCountry.equals(that.cardAcceptorCountry) : that.cardAcceptorCountry != null)
            return false;
        if (additionalDataRetailerId != null ? !additionalDataRetailerId.equals(that.additionalDataRetailerId) : that.additionalDataRetailerId != null)
            return false;
        if (currencyCode != null ? !currencyCode.equals(that.currencyCode) : that.currencyCode != null) return false;
        if (cardholderAuthenticationid != null ? !cardholderAuthenticationid.equals(that.cardholderAuthenticationid) : that.cardholderAuthenticationid != null)
            return false;
        if (cardholderAuthenticationdata != null ? !cardholderAuthenticationdata.equals(that.cardholderAuthenticationdata) : that.cardholderAuthenticationdata != null)
            return false;
        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        if (posEntryModePinCapability != null ? !posEntryModePinCapability.equals(that.posEntryModePinCapability) : that.posEntryModePinCapability != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (tokendataQkMgTrxIdx != null ? tokendataQkMgTrxIdx.hashCode() : 0);
        result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
        result = 31 * result + (systemsTraceAuditNum != null ? systemsTraceAuditNum.hashCode() : 0);
        result = 31 * result + (retrievalReferenceNum != null ? retrievalReferenceNum.hashCode() : 0);
        result = 31 * result + (processingCodeTransactionType != null ? processingCodeTransactionType.hashCode() : 0);
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        result = 31 * result + (processingCodeAccountTypeFrom != null ? processingCodeAccountTypeFrom.hashCode() : 0);
        result = 31 * result + (processingCodeAccountTypeTo != null ? processingCodeAccountTypeTo.hashCode() : 0);
        result = 31 * result + (pan != null ? pan.hashCode() : 0);
        result = 31 * result + (purchaseTime != null ? purchaseTime.hashCode() : 0);
        result = 31 * result + (purchaseDate != null ? purchaseDate.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        result = 31 * result + (posEntryModePanEntryMode != null ? posEntryModePanEntryMode.hashCode() : 0);
        result = 31 * result + (posConditionCode != null ? posConditionCode.hashCode() : 0);
        result = 31 * result + (acquiringInstitutionIdCode != null ? acquiringInstitutionIdCode.hashCode() : 0);
        result = 31 * result + (cardAcceptorTerminalId != null ? cardAcceptorTerminalId.hashCode() : 0);
        result = 31 * result + (cardAcceptorIdCode != null ? cardAcceptorIdCode.hashCode() : 0);
        result = 31 * result + (cardAcceptorName != null ? cardAcceptorName.hashCode() : 0);
        result = 31 * result + (cardAcceptorCity != null ? cardAcceptorCity.hashCode() : 0);
        result = 31 * result + (cardAcceptorState != null ? cardAcceptorState.hashCode() : 0);
        result = 31 * result + (cardAcceptorCountry != null ? cardAcceptorCountry.hashCode() : 0);
        result = 31 * result + (additionalDataRetailerId != null ? additionalDataRetailerId.hashCode() : 0);
        result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
        result = 31 * result + (cardholderAuthenticationid != null ? cardholderAuthenticationid.hashCode() : 0);
        result = 31 * result + (cardholderAuthenticationdata != null ? cardholderAuthenticationdata.hashCode() : 0);
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        result = 31 * result + (posEntryModePinCapability != null ? posEntryModePinCapability.hashCode() : 0);
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

    @Basic
    @Column(name = "date_logged", nullable = true)
    public Timestamp getDateLogged() {
        return dateLogged;
    }

    public void setDateLogged(Timestamp dateLogged) {
        this.dateLogged = dateLogged;
    }

    @Basic
    @Column(name = "structured_data", nullable = true, length = 2147483647)
    public String getStructuredData() {
        return structuredData;
    }

    public void setStructuredData(String structuredData) {
        this.structuredData = structuredData;
    }

    @Basic
    @Column(name = "transmission_datetime", nullable = true, length = 2147483647)
    public String getTransmissionDatetime() {
        return transmissionDatetime;
    }

    public void setTransmissionDatetime(String transmissionDatetime) {
        this.transmissionDatetime = transmissionDatetime;
    }

    @Basic
    @Column(name = "auth_id_response", nullable = true, length = 2147483647)
    public String getAuthIdResponse() {
        return authIdResponse;
    }

    public void setAuthIdResponse(String authIdResponse) {
        this.authIdResponse = authIdResponse;
    }

    @Basic
    @Column(name = "result_code", nullable = true, length = 2147483647)
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @Basic
    @Column(name = "acquiring_institution_id_length", nullable = true, length = 2147483647)
    public String getAcquiringInstitutionIdLength() {
        return acquiringInstitutionIdLength;
    }

    public void setAcquiringInstitutionIdLength(String acquiringInstitutionIdLength) {
        this.acquiringInstitutionIdLength = acquiringInstitutionIdLength;
    }

    @Basic
    @Column(name = "original_data_elements_transaction_type", nullable = true, length = 2147483647)
    public String getOriginalDataElementsTransactionType() {
        return originalDataElementsTransactionType;
    }

    public void setOriginalDataElementsTransactionType(String originalDataElementsTransactionType) {
        this.originalDataElementsTransactionType = originalDataElementsTransactionType;
    }

    @Basic
    @Column(name = "original_systems_trace_audit_number", nullable = true, length = 2147483647)
    public String getOriginalSystemsTraceAuditNumber() {
        return originalSystemsTraceAuditNumber;
    }

    public void setOriginalSystemsTraceAuditNumber(String originalSystemsTraceAuditNumber) {
        this.originalSystemsTraceAuditNumber = originalSystemsTraceAuditNumber;
    }

    @Basic
    @Column(name = "original_transmission_date_and_time", nullable = true, length = 2147483647)
    public String getOriginalTransmissionDateAndTime() {
        return originalTransmissionDateAndTime;
    }

    public void setOriginalTransmissionDateAndTime(String originalTransmissionDateAndTime) {
        this.originalTransmissionDateAndTime = originalTransmissionDateAndTime;
    }

    @Basic
    @Column(name = "original_acquirer_institution_id_code", nullable = true, length = 2147483647)
    public String getOriginalAcquirerInstitutionIdCode() {
        return originalAcquirerInstitutionIdCode;
    }

    public void setOriginalAcquirerInstitutionIdCode(String originalAcquirerInstitutionIdCode) {
        this.originalAcquirerInstitutionIdCode = originalAcquirerInstitutionIdCode;
    }

    @Basic
    @Column(name = "original_forwarding_institution_id_code", nullable = true, length = 2147483647)
    public String getOriginalForwardingInstitutionIdCode() {
        return originalForwardingInstitutionIdCode;
    }

    public void setOriginalForwardingInstitutionIdCode(String originalForwardingInstitutionIdCode) {
        this.originalForwardingInstitutionIdCode = originalForwardingInstitutionIdCode;
    }

    @Basic
    @Column(name = "merchant_type", nullable = true, length = 2147483647)
    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    @Basic
    @Column(name = "extended_payment_code", nullable = true, length = 2147483647)
    public String getExtendedPaymentCode() {
        return extendedPaymentCode;
    }

    public void setExtendedPaymentCode(String extendedPaymentCode) {
        this.extendedPaymentCode = extendedPaymentCode;
    }

    @Basic
    @Column(name = "cardholder_authentication_capability", nullable = true, length = 2147483647)
    public String getCardholderAuthenticationCapability() {
        return cardholderAuthenticationCapability;
    }

    public void setCardholderAuthenticationCapability(String cardholderAuthenticationCapability) {
        this.cardholderAuthenticationCapability = cardholderAuthenticationCapability;
    }

    @Basic
    @Column(name = "cardholder_authentication_method", nullable = true, length = 2147483647)
    public String getCardholderAuthenticationMethod() {
        return cardholderAuthenticationMethod;
    }

    public void setCardholderAuthenticationMethod(String cardholderAuthenticationMethod) {
        this.cardholderAuthenticationMethod = cardholderAuthenticationMethod;
    }

    @Basic
    @Column(name = "cardholder_authentication_entity", nullable = true, length = 2147483647)
    public String getCardholderAuthenticationEntity() {
        return cardholderAuthenticationEntity;
    }

    public void setCardholderAuthenticationEntity(String cardholderAuthenticationEntity) {
        this.cardholderAuthenticationEntity = cardholderAuthenticationEntity;
    }

    @Basic
    @Column(name = "card_data_input_capability_position1", nullable = true, length = 2147483647)
    public String getCardDataInputCapabilityPosition1() {
        return cardDataInputCapabilityPosition1;
    }

    public void setCardDataInputCapabilityPosition1(String cardDataInputCapabilityPosition1) {
        this.cardDataInputCapabilityPosition1 = cardDataInputCapabilityPosition1;
    }

    @Basic
    @Column(name = "card_capture_capability", nullable = true, length = 2147483647)
    public String getCardCaptureCapability() {
        return cardCaptureCapability;
    }

    public void setCardCaptureCapability(String cardCaptureCapability) {
        this.cardCaptureCapability = cardCaptureCapability;
    }

    @Basic
    @Column(name = "operating_environment", nullable = true, length = 2147483647)
    public String getOperatingEnvironment() {
        return operatingEnvironment;
    }

    public void setOperatingEnvironment(String operatingEnvironment) {
        this.operatingEnvironment = operatingEnvironment;
    }

    @Basic
    @Column(name = "cardholder_is_present", nullable = true, length = 2147483647)
    public String getCardholderIsPresent() {
        return cardholderIsPresent;
    }

    public void setCardholderIsPresent(String cardholderIsPresent) {
        this.cardholderIsPresent = cardholderIsPresent;
    }

    @Basic
    @Column(name = "card_is_present", nullable = true, length = 2147483647)
    public String getCardIsPresent() {
        return cardIsPresent;
    }

    public void setCardIsPresent(String cardIsPresent) {
        this.cardIsPresent = cardIsPresent;
    }

    @Basic
    @Column(name = "card_data_input_capability_position7", nullable = true, length = 2147483647)
    public String getCardDataInputCapabilityPosition7() {
        return cardDataInputCapabilityPosition7;
    }

    public void setCardDataInputCapabilityPosition7(String cardDataInputCapabilityPosition7) {
        this.cardDataInputCapabilityPosition7 = cardDataInputCapabilityPosition7;
    }

    @Basic
    @Column(name = "card_data_output_capability", nullable = true, length = 2147483647)
    public String getCardDataOutputCapability() {
        return cardDataOutputCapability;
    }

    public void setCardDataOutputCapability(String cardDataOutputCapability) {
        this.cardDataOutputCapability = cardDataOutputCapability;
    }

    @Basic
    @Column(name = "terminal_output_capability", nullable = true, length = 2147483647)
    public String getTerminalOutputCapability() {
        return terminalOutputCapability;
    }

    public void setTerminalOutputCapability(String terminalOutputCapability) {
        this.terminalOutputCapability = terminalOutputCapability;
    }

    @Basic
    @Column(name = "pin_capture_capability", nullable = true, length = 2147483647)
    public String getPinCaptureCapability() {
        return pinCaptureCapability;
    }

    public void setPinCaptureCapability(String pinCaptureCapability) {
        this.pinCaptureCapability = pinCaptureCapability;
    }

    @Basic
    @Column(name = "terminal_operator", nullable = true, length = 2147483647)
    public String getTerminalOperator() {
        return terminalOperator;
    }

    public void setTerminalOperator(String terminalOperator) {
        this.terminalOperator = terminalOperator;
    }

    @Basic
    @Column(name = "authorization_profile", nullable = true, length = 2147483647)
    public String getAuthorizationProfile() {
        return authorizationProfile;
    }

    public void setAuthorizationProfile(String authorizationProfile) {
        this.authorizationProfile = authorizationProfile;
    }

    @Basic
    @Column(name = "terminal_type", nullable = true, length = 2147483647)
    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    @Basic
    @Column(name = "card_verification_result", nullable = true, length = 2147483647)
    public String getCardVerificationResult() {
        return cardVerificationResult;
    }

    public void setCardVerificationResult(String cardVerificationResult) {
        this.cardVerificationResult = cardVerificationResult;
    }

    @Basic
    @Column(name = "ucaf_collection", nullable = true, length = 2147483647)
    public String getUcafCollection() {
        return ucafCollection;
    }

    public void setUcafCollection(String ucafCollection) {
        this.ucafCollection = ucafCollection;
    }

    @Basic
    @Column(name = "ucaf_authentication", nullable = true, length = 2147483647)
    public String getUcafAuthentication() {
        return ucafAuthentication;
    }

    public void setUcafAuthentication(String ucafAuthentication) {
        this.ucafAuthentication = ucafAuthentication;
    }
}
