package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "card_vacp_response", schema = "dbo", catalog = "transaction_db")
public class CardVacpResponseEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "date_logged")
    private Timestamp dateLogged;
    @Basic@Column(name = "transaction_leg_id")
    private long transactionLegId;
    @Basic@Column(name = "merchant_reference_code")
    private String merchantReferenceCode;
    @Basic@Column(name = "request_id")
    private String requestId;
    @Basic@Column(name = "direction")
    private String direction;
    @Basic@Column(name = "reason_code")
    private Long reasonCode;
    @Basic@Column(name = "request_token")
    private String requestToken;
    @Basic@Column(name = "currency")
    private String currency;
    @Basic@Column(name = "amount")
    private String amount;
    @Basic@Column(name = "reply_reason_code")
    private Long replyReasonCode;
    @Basic@Column(name = "request_date_time")
    private String requestDateTime;
    @Basic@Column(name = "reconciliation_id")
    private String reconciliationId;
    @Basic@Column(name = "processor_response")
    private String processorResponse;
    @Basic@Column(name = "authorisation_code")
    private String authorisationCode;
    @Basic@Column(name = "avs_code")
    private String avsCode;
    @Basic@Column(name = "avs_code_raw")
    private String avsCodeRaw;
    @Basic@Column(name = "cv_code")
    private String cvCode;
    @Basic@Column(name = "cv_code_raw")
    private String cvCodeRaw;
    @Basic@Column(name = "merchant_advice_code")
    private String merchantAdviceCode;
    @Basic@Column(name = "merchant_advice_code_raw")
    private String merchantAdviceCodeRaw;
    @Basic@Column(name = "cavv_response_code")
    private String cavvResponseCode;
    @Basic@Column(name = "cavv_response_code_raw")
    private String cavvResponseCodeRaw;
    @Basic@Column(name = "payment_network_transaction_id")
    private String paymentNetworkTransactionId;
    @Basic@Column(name = "card_category")
    private String cardCategory;
    @Basic@Column(name = "card_group")
    private String cardGroup;
    @Basic@Column(name = "request_currency")
    private String requestCurrency;
    @Basic@Column(name = "receipt_number")
    private String receiptNumber;
    @Basic@Column(name = "additional_data")
    private String additionalData;

}
