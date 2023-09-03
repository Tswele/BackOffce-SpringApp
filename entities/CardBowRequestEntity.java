package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "card_bow_request", schema = "dbo", catalog = "transaction_db")
public class CardBowRequestEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "date_logged")
    private Timestamp dateLogged;
    @Basic@Column(name = "transaction_leg_id")
    private long transactionLegId;
    @Basic@Column(name = "merchant_id")
    private String merchantId;
    @Basic@Column(name = "merchant_reference_code")
    private String merchantReferenceCode;
    @Basic@Column(name = "bill_to_first_name")
    private String billToFirstName;
    @Basic@Column(name = "bill_to_last_name")
    private String billToLastName;
    @Basic@Column(name = "bill_to_street_1")
    private String billToStreet1;
    @Basic@Column(name = "bill_to_city")
    private String billToCity;
    @Basic@Column(name = "bill_to_postal_code")
    private String billToPostalCode;
    @Basic@Column(name = "bill_to_country")
    private String billToCountry;
    @Basic@Column(name = "bill_to_email")
    private String billToEmail;
    @Basic@Column(name = "purchase_totals_currency")
    private String purchaseTotalsCurrency;
    @Basic@Column(name = "purchase_totals_grand_total_amount")
    private String purchaseTotalsGrandTotalAmount;
    @Basic@Column(name = "card_account_number")
    private String cardAccountNumber;
    @Basic@Column(name = "card_expiration_month")
    private String cardExpirationMonth;
    @Basic@Column(name = "card_expiration_year")
    private String cardExpirationYear;
    @Basic@Column(name = "card_type")
    private String cardType;
    @Basic@Column(name = "cc_auth_service_cavv")
    private String ccAuthServiceCavv;
    @Basic@Column(name = "cc_auth_service_commerce_indicator")
    private String ccAuthServiceCommerceIndicator;
    @Basic@Column(name = "cc_auth_service_xid")
    private String ccAuthServiceXid;
    @Basic@Column(name = "cc_auth_service_partial_auth_indicator")
    private String ccAuthServicePartialAuthIndicator;
    @Basic@Column(name = "cc_capture_service_auth_request_id")
    private String ccCaptureServiceAuthRequestId;
    @Basic@Column(name = "cc_reversal_service_auth_request_id")
    private String ccReversalServiceAuthRequestId;
    @Basic@Column(name = "cc_credit_service_capture_request_id")
    private String ccCreditServiceCaptureRequestId;
    @Basic@Column(name = "merchant_transaction_identifier")
    private String merchantTransactionIdentifier;

}
