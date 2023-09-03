package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "card_postillion_response", schema = "dbo", catalog = "transaction_db")
public class CardPostillionResponseEntity {
    private long id;
    private String posBatchData;
    private String authIdResponse;
    private String posSettlementData;
    private String posCardIssuer;
    private String purchaseDate;
    private String merchantType;
    private String posEntryMode;
    private String posPreAuthChargeback;
    private String transmissionDatetime;
    private String receivingInstitutionIdCode;
    private String resultDescription;
    private String cardAcceptorNameLocation;
    private String cardAcceptorIdCode;
    private Long amount;
    private String track2;
    private String processingCode;
    private String resultCode;
    private String posAuthIndicators;
    private String expiryData;
    private String captureDate;
    private String additionalDataRetailerData;
    private Long transactionId;
    private String resultSource;
    private String currencyCode;
    private String systemsTraceAuditNum;
    private String purchaseTime;
    private String acquiringInstitutionIdCode;
    private String posTerminalData;
    private String settlementDate;
    private String cardAcceptorTerminalId;
    private String resultStatus;
    private String retrievalReferenceNum;
    private String tokendata;
    private TransactionLegEntity transactionLegByTransactionLegId;
    private Timestamp dateLogged;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pos_batch_data", nullable = true, length = 9)
    public String getPosBatchData() {
        return posBatchData;
    }

    public void setPosBatchData(String posBatchData) {
        this.posBatchData = posBatchData;
    }

    @Basic
    @Column(name = "auth_id_response", nullable = true, length = 6)
    public String getAuthIdResponse() {
        return authIdResponse;
    }

    public void setAuthIdResponse(String authIdResponse) {
        this.authIdResponse = authIdResponse;
    }

    @Basic
    @Column(name = "pos_settlement_data", nullable = true, length = 2147483647)
    public String getPosSettlementData() {
        return posSettlementData;
    }

    public void setPosSettlementData(String posSettlementData) {
        this.posSettlementData = posSettlementData;
    }

    @Basic
    @Column(name = "pos_card_issuer", nullable = true, length = 2147483647)
    public String getPosCardIssuer() {
        return posCardIssuer;
    }

    public void setPosCardIssuer(String posCardIssuer) {
        this.posCardIssuer = posCardIssuer;
    }

    @Basic
    @Column(name = "purchase_date", nullable = true, length = 4)
    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Basic
    @Column(name = "merchant_type", nullable = true, length = 4)
    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    @Basic
    @Column(name = "pos_entry_mode", nullable = true, length = 3)
    public String getPosEntryMode() {
        return posEntryMode;
    }

    public void setPosEntryMode(String posEntryMode) {
        this.posEntryMode = posEntryMode;
    }

    @Basic
    @Column(name = "pos_pre_auth_chargeback", nullable = true, length = 2147483647)
    public String getPosPreAuthChargeback() {
        return posPreAuthChargeback;
    }

    public void setPosPreAuthChargeback(String posPreAuthChargeback) {
        this.posPreAuthChargeback = posPreAuthChargeback;
    }

    @Basic
    @Column(name = "transmission_datetime", nullable = true, length = 10)
    public String getTransmissionDatetime() {
        return transmissionDatetime;
    }

    public void setTransmissionDatetime(String transmissionDatetime) {
        this.transmissionDatetime = transmissionDatetime;
    }

    @Basic
    @Column(name = "receiving_institution_id_code", nullable = true, length = 11)
    public String getReceivingInstitutionIdCode() {
        return receivingInstitutionIdCode;
    }

    public void setReceivingInstitutionIdCode(String receivingInstitutionIdCode) {
        this.receivingInstitutionIdCode = receivingInstitutionIdCode;
    }

    @Basic
    @Column(name = "result_description", nullable = true, length = 50)
    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    @Basic
    @Column(name = "card_acceptor_name_location", nullable = true, length = 2147483647)
    public String getCardAcceptorNameLocation() {
        return cardAcceptorNameLocation;
    }

    public void setCardAcceptorNameLocation(String cardAcceptorNameLocation) {
        this.cardAcceptorNameLocation = cardAcceptorNameLocation;
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
    @Column(name = "amount", nullable = true)
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "track2", nullable = true, length = 15)
    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    @Basic
    @Column(name = "processing_code", nullable = true, length = 6)
    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    @Basic
    @Column(name = "result_code", nullable = true, length = 2)
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @Basic
    @Column(name = "pos_auth_indicators", nullable = true, length = 2147483647)
    public String getPosAuthIndicators() {
        return posAuthIndicators;
    }

    public void setPosAuthIndicators(String posAuthIndicators) {
        this.posAuthIndicators = posAuthIndicators;
    }

    @Basic
    @Column(name = "expiry_data", nullable = true, length = 4)
    public String getExpiryData() {
        return expiryData;
    }

    public void setExpiryData(String expiryData) {
        this.expiryData = expiryData;
    }

    @Basic
    @Column(name = "capture_date", nullable = true, length = 4)
    public String getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(String captureDate) {
        this.captureDate = captureDate;
    }

    @Basic
    @Column(name = "additional_data_retailer_data", nullable = true, length = 2147483647)
    public String getAdditionalDataRetailerData() {
        return additionalDataRetailerData;
    }

    public void setAdditionalDataRetailerData(String additionalDataRetailerData) {
        this.additionalDataRetailerData = additionalDataRetailerData;
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
    @Column(name = "result_source", nullable = true, length = 4)
    public String getResultSource() {
        return resultSource;
    }

    public void setResultSource(String resultSource) {
        this.resultSource = resultSource;
    }

    @Basic
    @Column(name = "currency_code", nullable = true, length = 3)
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Basic
    @Column(name = "systems_trace_audit_num", nullable = true, length = 5)
    public String getSystemsTraceAuditNum() {
        return systemsTraceAuditNum;
    }

    public void setSystemsTraceAuditNum(String systemsTraceAuditNum) {
        this.systemsTraceAuditNum = systemsTraceAuditNum;
    }

    @Basic
    @Column(name = "purchase_time", nullable = true, length = 6)
    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Basic
    @Column(name = "acquiring_institution_id_code", nullable = true, length = 10)
    public String getAcquiringInstitutionIdCode() {
        return acquiringInstitutionIdCode;
    }

    public void setAcquiringInstitutionIdCode(String acquiringInstitutionIdCode) {
        this.acquiringInstitutionIdCode = acquiringInstitutionIdCode;
    }

    @Basic
    @Column(name = "pos_terminal_data", nullable = true, length = 2147483647)
    public String getPosTerminalData() {
        return posTerminalData;
    }

    public void setPosTerminalData(String posTerminalData) {
        this.posTerminalData = posTerminalData;
    }

    @Basic
    @Column(name = "settlement_date", nullable = true, length = 4)
    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
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
    @Column(name = "result_status", nullable = true, length = 1)
    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    @Basic
    @Column(name = "retrieval_reference_num", nullable = true, length = 12)
    public String getRetrievalReferenceNum() {
        return retrievalReferenceNum;
    }

    public void setRetrievalReferenceNum(String retrievalReferenceNum) {
        this.retrievalReferenceNum = retrievalReferenceNum;
    }

    @Basic
    @Column(name = "tokendata", nullable = true, length = 2147483647)
    public String getTokendata() {
        return tokendata;
    }

    public void setTokendata(String tokendata) {
        this.tokendata = tokendata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardPostillionResponseEntity that = (CardPostillionResponseEntity) o;

        if (id != that.id) return false;
        if (posBatchData != null ? !posBatchData.equals(that.posBatchData) : that.posBatchData != null) return false;
        if (authIdResponse != null ? !authIdResponse.equals(that.authIdResponse) : that.authIdResponse != null)
            return false;
        if (posSettlementData != null ? !posSettlementData.equals(that.posSettlementData) : that.posSettlementData != null)
            return false;
        if (posCardIssuer != null ? !posCardIssuer.equals(that.posCardIssuer) : that.posCardIssuer != null)
            return false;
        if (purchaseDate != null ? !purchaseDate.equals(that.purchaseDate) : that.purchaseDate != null) return false;
        if (merchantType != null ? !merchantType.equals(that.merchantType) : that.merchantType != null) return false;
        if (posEntryMode != null ? !posEntryMode.equals(that.posEntryMode) : that.posEntryMode != null) return false;
        if (posPreAuthChargeback != null ? !posPreAuthChargeback.equals(that.posPreAuthChargeback) : that.posPreAuthChargeback != null)
            return false;
        if (transmissionDatetime != null ? !transmissionDatetime.equals(that.transmissionDatetime) : that.transmissionDatetime != null)
            return false;
        if (receivingInstitutionIdCode != null ? !receivingInstitutionIdCode.equals(that.receivingInstitutionIdCode) : that.receivingInstitutionIdCode != null)
            return false;
        if (resultDescription != null ? !resultDescription.equals(that.resultDescription) : that.resultDescription != null)
            return false;
        if (cardAcceptorNameLocation != null ? !cardAcceptorNameLocation.equals(that.cardAcceptorNameLocation) : that.cardAcceptorNameLocation != null)
            return false;
        if (cardAcceptorIdCode != null ? !cardAcceptorIdCode.equals(that.cardAcceptorIdCode) : that.cardAcceptorIdCode != null)
            return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (track2 != null ? !track2.equals(that.track2) : that.track2 != null) return false;
        if (processingCode != null ? !processingCode.equals(that.processingCode) : that.processingCode != null)
            return false;
        if (resultCode != null ? !resultCode.equals(that.resultCode) : that.resultCode != null) return false;
        if (posAuthIndicators != null ? !posAuthIndicators.equals(that.posAuthIndicators) : that.posAuthIndicators != null)
            return false;
        if (expiryData != null ? !expiryData.equals(that.expiryData) : that.expiryData != null) return false;
        if (captureDate != null ? !captureDate.equals(that.captureDate) : that.captureDate != null) return false;
        if (additionalDataRetailerData != null ? !additionalDataRetailerData.equals(that.additionalDataRetailerData) : that.additionalDataRetailerData != null)
            return false;
        if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
            return false;
        if (resultSource != null ? !resultSource.equals(that.resultSource) : that.resultSource != null) return false;
        if (currencyCode != null ? !currencyCode.equals(that.currencyCode) : that.currencyCode != null) return false;
        if (systemsTraceAuditNum != null ? !systemsTraceAuditNum.equals(that.systemsTraceAuditNum) : that.systemsTraceAuditNum != null)
            return false;
        if (purchaseTime != null ? !purchaseTime.equals(that.purchaseTime) : that.purchaseTime != null) return false;
        if (acquiringInstitutionIdCode != null ? !acquiringInstitutionIdCode.equals(that.acquiringInstitutionIdCode) : that.acquiringInstitutionIdCode != null)
            return false;
        if (posTerminalData != null ? !posTerminalData.equals(that.posTerminalData) : that.posTerminalData != null)
            return false;
        if (settlementDate != null ? !settlementDate.equals(that.settlementDate) : that.settlementDate != null)
            return false;
        if (cardAcceptorTerminalId != null ? !cardAcceptorTerminalId.equals(that.cardAcceptorTerminalId) : that.cardAcceptorTerminalId != null)
            return false;
        if (resultStatus != null ? !resultStatus.equals(that.resultStatus) : that.resultStatus != null) return false;
        if (retrievalReferenceNum != null ? !retrievalReferenceNum.equals(that.retrievalReferenceNum) : that.retrievalReferenceNum != null)
            return false;
        if (tokendata != null ? !tokendata.equals(that.tokendata) : that.tokendata != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (posBatchData != null ? posBatchData.hashCode() : 0);
        result = 31 * result + (authIdResponse != null ? authIdResponse.hashCode() : 0);
        result = 31 * result + (posSettlementData != null ? posSettlementData.hashCode() : 0);
        result = 31 * result + (posCardIssuer != null ? posCardIssuer.hashCode() : 0);
        result = 31 * result + (purchaseDate != null ? purchaseDate.hashCode() : 0);
        result = 31 * result + (merchantType != null ? merchantType.hashCode() : 0);
        result = 31 * result + (posEntryMode != null ? posEntryMode.hashCode() : 0);
        result = 31 * result + (posPreAuthChargeback != null ? posPreAuthChargeback.hashCode() : 0);
        result = 31 * result + (transmissionDatetime != null ? transmissionDatetime.hashCode() : 0);
        result = 31 * result + (receivingInstitutionIdCode != null ? receivingInstitutionIdCode.hashCode() : 0);
        result = 31 * result + (resultDescription != null ? resultDescription.hashCode() : 0);
        result = 31 * result + (cardAcceptorNameLocation != null ? cardAcceptorNameLocation.hashCode() : 0);
        result = 31 * result + (cardAcceptorIdCode != null ? cardAcceptorIdCode.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (track2 != null ? track2.hashCode() : 0);
        result = 31 * result + (processingCode != null ? processingCode.hashCode() : 0);
        result = 31 * result + (resultCode != null ? resultCode.hashCode() : 0);
        result = 31 * result + (posAuthIndicators != null ? posAuthIndicators.hashCode() : 0);
        result = 31 * result + (expiryData != null ? expiryData.hashCode() : 0);
        result = 31 * result + (captureDate != null ? captureDate.hashCode() : 0);
        result = 31 * result + (additionalDataRetailerData != null ? additionalDataRetailerData.hashCode() : 0);
        result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
        result = 31 * result + (resultSource != null ? resultSource.hashCode() : 0);
        result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
        result = 31 * result + (systemsTraceAuditNum != null ? systemsTraceAuditNum.hashCode() : 0);
        result = 31 * result + (purchaseTime != null ? purchaseTime.hashCode() : 0);
        result = 31 * result + (acquiringInstitutionIdCode != null ? acquiringInstitutionIdCode.hashCode() : 0);
        result = 31 * result + (posTerminalData != null ? posTerminalData.hashCode() : 0);
        result = 31 * result + (settlementDate != null ? settlementDate.hashCode() : 0);
        result = 31 * result + (cardAcceptorTerminalId != null ? cardAcceptorTerminalId.hashCode() : 0);
        result = 31 * result + (resultStatus != null ? resultStatus.hashCode() : 0);
        result = 31 * result + (retrievalReferenceNum != null ? retrievalReferenceNum.hashCode() : 0);
        result = 31 * result + (tokendata != null ? tokendata.hashCode() : 0);
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
    @Column(name = "date_logged", nullable = false)
    public Timestamp getDateLogged() {
        return dateLogged;
    }

    public void setDateLogged(Timestamp dateLogged) {
        this.dateLogged = dateLogged;
    }
}
