package za.co.wirecard.channel.backoffice.dto.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import za.co.wirecard.channel.backoffice.entities.CardBatchRecordEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class CardBatchRecord {
    private long id;
    private String transactionUid;
    private String transactionStatus;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp transactionInitiatedDate;
    private String transactionAuthorisationCode;
    private BigDecimal transactionAmount;
    private String merchantReference;

    private String initiationFailureMessage;
    private String transactionCardHolder;
    private String transactionCardNumber;
    private String transactionCardNumberHash;
    private String transactionCardType;
    private String transactionCardCountry;
    private String transactionThreeDSecureCode;
    private String transactionThreeDSecureDescription;
    private String transactionBankResponseCode;
    private String transactionResponseCode;
    private String transactionBankResponseMessage;
    private BigDecimal transactionAuthorisedAmount;
    private BigDecimal transactionSettledAmount;
    private BigDecimal transactionRefundedAmount;

    public CardBatchRecord(CardBatchRecordEntity cardBatchRecordEntity) {
        this.id = cardBatchRecordEntity.getId();
        this.transactionUid = cardBatchRecordEntity.getTransactionUid();
        this.transactionStatus = cardBatchRecordEntity.getTransactionStatus();
        this.transactionInitiatedDate = cardBatchRecordEntity.getTransactionInitiatedDate();
        this.transactionAuthorisationCode = cardBatchRecordEntity.getTransactionAuthorisationCode();
        this.transactionAmount = cardBatchRecordEntity.getTransactionAmount();
        this.merchantReference = cardBatchRecordEntity.getMerchantReference();
        this.initiationFailureMessage = cardBatchRecordEntity.getInitiationFailureMessage();
        this.transactionCardHolder = cardBatchRecordEntity.getTransactionCardHolder();
        this.transactionCardNumber = cardBatchRecordEntity.getTransactionCardNumber();
        this.transactionCardNumberHash = cardBatchRecordEntity.getTransactionCardNumberHash();
        this.transactionCardType = cardBatchRecordEntity.getTransactionCardType();
        this.transactionCardCountry = cardBatchRecordEntity.getTransactionCardCountry();
        this.transactionThreeDSecureCode = cardBatchRecordEntity.getTransactionThreeDSecureCode();
        this.transactionThreeDSecureDescription = cardBatchRecordEntity.getTransactionThreeDSecureDescription();
        this.transactionBankResponseCode = cardBatchRecordEntity.getTransactionBankResponseCode();
        this.transactionResponseCode = cardBatchRecordEntity.getTransactionResponseCode();;
        this.transactionBankResponseMessage = cardBatchRecordEntity.getTransactionBankResponseMessage();
        this.transactionAuthorisedAmount = cardBatchRecordEntity.getTransactionAuthorisedAmount();
        this.transactionSettledAmount = cardBatchRecordEntity.getTransactionSettledAmount();
        this.transactionRefundedAmount = cardBatchRecordEntity.getTransactionRefundedAmount();
    }
}
