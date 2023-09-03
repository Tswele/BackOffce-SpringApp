package za.co.wirecard.channel.backoffice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import za.co.wirecard.channel.backoffice.entities.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    private long id;
    private PaymentType paymentType;
    private Gateway gateway;
    private TransactionState transactionState;
    private PaymentInterface paymentInterface;
    private PaymentSubType paymentSubType;
    private String errorMessage;
    private String merchantCompanyName;
    private String applicationName;
    private String merchantReference;
    private String transactionUid;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    private LocalDateTime initiationDate;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    private Date lastUpdated;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    private Date completedDate;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    private Date transactionDate;
    private BigDecimal requestedAmount;
    private BigDecimal authorisedAmount;
    private BigDecimal settledAmount;
    private BigDecimal refundedAmount;
    private String purchaserFullName;
    private String purchaserEmail;
    private String purchaserMsisdn;
    private String issuingBank;

    private String cardType;
    // card specific
    private CardDetails cardDetails;

    public Transaction (TransactionEntity transactionEntity,
                        CardTransactionEntity cardTransactionEntity,
                        ThreeDsAuthEntity threeDsAuthEntity,
                        ApplicationEntity applicationEntity){
        this.id = transactionEntity.getId();
        this.setMerchantCompanyName(transactionEntity.getMerchantByMerchantId().getCompanyName());
        this.setErrorMessage(transactionEntity.getErrorMessage());
        this.setMerchantReference(transactionEntity.getMerchantReference());
        this.setTransactionUid(transactionEntity.getTransactionUid());
        this.setInitiationDate(transactionEntity.getInitiationDate().toLocalDateTime());
        this.setLastUpdated(transactionEntity.getLastUpdatedDate());
        this.setCompletedDate(transactionEntity.getCompletedDate());
        this.setTransactionDate(transactionEntity.getInitiationDate());
        this.setRequestedAmount(transactionEntity.getTransactionValue());
        this.setAuthorisedAmount(transactionEntity.getAuthorisedValue());
        this.setSettledAmount(transactionEntity.getSettledValue());
        this.setRefundedAmount(transactionEntity.getRefundValue());
        this.setPurchaserFullName(transactionEntity.getPurchaserFullName());
        this.setPurchaserEmail(transactionEntity.getPurchaserEmail());
        this.setPurchaserMsisdn(transactionEntity.getPurchaserMsisdn());
        this.setPaymentType(new PaymentType(transactionEntity.getPaymentTypeByPaymentTypeId()));
        this.setGateway(new Gateway(transactionEntity.getGatewayByGatewayId()));
        this.setTransactionState(new TransactionState(transactionEntity.getTransactionStateByTransactionStateId()));
        this.setPaymentInterface(new PaymentInterface(transactionEntity.getInterfaceByInterfaceId()));
        if (applicationEntity != null) {
            this.setApplicationName(applicationEntity.getName());
        }
        if (transactionEntity.getPaymentSubtypeByPaymentSubtypeId() != null) {
            this.setPaymentSubType(new PaymentSubType(transactionEntity.getPaymentSubtypeByPaymentSubtypeId()));
        }
        if (cardTransactionEntity != null){
            this.setIssuingBank(cardTransactionEntity.getIssuingBank());
            this.setCardType(cardTransactionEntity.getCardType());
        } else {
            this.setIssuingBank("");
            this.setCardType("");
        }
        if (transactionEntity.getPaymentTypeByPaymentTypeId().getCode().equalsIgnoreCase("card")) {
            this.cardDetails = new CardDetails(
                    cardTransactionEntity != null ? StringUtils.isNotBlank(cardTransactionEntity.getCardholderFullname()) ? cardTransactionEntity.getCardholderFullname() : "" : "",
                    cardTransactionEntity != null ? StringUtils.isNotBlank(cardTransactionEntity.getCardBin()) && StringUtils.isNotBlank(cardTransactionEntity.getCardLastFour()) ? cardTransactionEntity.getCardBin() + "******" + cardTransactionEntity.getCardLastFour() : "" : "",
                    cardTransactionEntity != null ? StringUtils.isNotBlank(cardTransactionEntity.getCardType()) ? cardTransactionEntity.getCardType() : "" : "",
                    threeDsAuthEntity != null && threeDsAuthEntity.getThreeDsTransactionByThreeDsTransactionId() != null && StringUtils.isNotBlank(threeDsAuthEntity.getThreeDsTransactionByThreeDsTransactionId().getEci()) ? threeDsAuthEntity.getThreeDsTransactionByThreeDsTransactionId().getEci() :
                            transactionEntity.getTransactionStateByTransactionStateId().getCode().equalsIgnoreCase("FAILED") || transactionEntity.getTransactionStateByTransactionStateId().getCode().equalsIgnoreCase("TIMED_OUT") ? "none" : "07",
                    cardTransactionEntity != null ? StringUtils.isNotBlank(cardTransactionEntity.getAuthorisationId()) ? cardTransactionEntity.getAuthorisationId() : "" : ""
            );
        }

    }

}
