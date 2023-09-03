package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "card_batch_record")
public class CardBatchRecordEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "value", nullable = false, precision = 2)
    private BigDecimal value;
    @Basic@Column(name = "budget_period", nullable = false)
    private short budgetPeriod;
    @Basic@Column(name = "description", nullable = true, length = 50)
    private String description;
    @Basic@Column(name = "originating_transaction_id", nullable = true, length = 50)
    private String originatingTransactionId;
    @Basic@Column(name = "merchant_reference", nullable = true, length = 100)
    private String merchantReference;
    @Basic@Column(name = "card_number", nullable = true, length = 2147483647)
    private String cardNumber;
    @Basic@Column(name = "expiry_year", nullable = true)
    private Short expiryYear;
    @Basic@Column(name = "expiry_month", nullable = true)
    private Short expiryMonth;
    @Basic@Column(name = "cardholder_fullname", nullable = true, length = 50)
    private String cardholderFullname;
    @Basic@Column(name = "profile_uid", nullable = true, length = 2147483647)
    private String profileUid;
    @Basic@Column(name = "token", nullable = true, length = 2147483647)
    private String token;
    @Basic@Column(name = "uci", nullable = true, length = 255)
    private String uci;
    @Basic@Column(name = "transaction_id", nullable = true)
    private Long transactionId;
    @Basic@Column(name = "billing_customer_id", nullable = true, length = 50)
    private String billingCustomerId;
    @Basic@Column(name = "billing_invoice_id", nullable = true, length = 50)
    private String billingInvoiceId;
    @Basic@Column(name = "billing_invoice_description", nullable = true, length = 50)
    private String billingInvoiceDescription;
    @Basic@Column(name = "billing_contact_full_name", nullable = true, length = 50)
    private String billingContactFullName;
    @Basic@Column(name = "billing_contact_first_name", nullable = true, length = 50)
    private String billingContactFirstName;
    @Basic@Column(name = "billing_contact_last_name", nullable = true, length = 50)
    private String billingContactLastName;
    @Basic@Column(name = "billing_contact_company", nullable = true, length = 50)
    private String billingContactCompany;
    @Basic@Column(name = "billing_contact_number", nullable = true, length = 50)
    private String billingContactNumber;
    @Basic@Column(name = "billing_contact_alternative_number", nullable = true, length = 50)
    private String billingContactAlternativeNumber;
    @Basic@Column(name = "billing_contact_email", nullable = true, length = 50)
    private String billingContactEmail;
    @Basic@Column(name = "shipping_method", nullable = true, length = 50)
    private String shippingMethod;
    @Basic@Column(name = "shipping_method_indicator", nullable = true, length = 50)
    private String shippingMethodIndicator;
    @Basic@Column(name = "shipping_name_indicator", nullable = true, length = 50)
    private String shippingNameIndicator;
    @Basic@Column(name = "shipping_delivery_timeframe", nullable = true, length = 50)
    private String shippingDeliveryTimeframe;
    @Basic@Column(name = "shipping_delivery_email", nullable = true, length = 50)
    private String shippingDeliveryEmail;
    @Basic@Column(name = "shipping_contact_full_name", nullable = true, length = 50)
    private String shippingContactFullName;
    @Basic@Column(name = "shipping_contact_first_name", nullable = true, length = 50)
    private String shippingContactFirstName;
    @Basic@Column(name = "shipping_contact_last_name", nullable = true, length = 50)
    private String shippingContactLastName;
    @Basic@Column(name = "shipping_contact_company", nullable = true, length = 50)
    private String shippingContactCompany;
    @Basic@Column(name = "shipping_contact_contact_number", nullable = true, length = 50)
    private String shippingContactContactNumber;
    @Basic@Column(name = "shipping_contact_contact_alternative_number", nullable = true, length = 50)
    private String shippingContactContactAlternativeNumber;
    @Basic@Column(name = "shipping_contact_contact_email", nullable = true, length = 50)
    private String shippingContactContactEmail;
    @Basic@Column(name = "shipping_address_address2", nullable = true, length = 50)
    private String shippingAddressAddress2;
    @Basic@Column(name = "shipping_address_address3", nullable = true, length = 50)
    private String shippingAddressAddress3;
    @Basic@Column(name = "shipping_address_address4", nullable = true, length = 50)
    private String shippingAddressAddress4;
    @Basic@Column(name = "shipping_address_address5", nullable = true, length = 50)
    private String shippingAddressAddress5;
    @Basic@Column(name = "shipping_address_address1", nullable = true, length = 50)
    private String shippingAddressAddress1;
    @Basic@Column(name = "shipping_address_suburb", nullable = true, length = 50)
    private String shippingAddressSuburb;
    @Basic@Column(name = "shipping_address_city", nullable = true, length = 50)
    private String shippingAddressCity;
    @Basic@Column(name = "shipping_address_postal_code", nullable = true, length = 50)
    private String shippingAddressPostalCode;
    @Basic@Column(name = "shipping_address_country", nullable = true, length = 50)
    private String shippingAddressCountry;
    @Basic@Column(name = "shipping_address_usage_indicator", nullable = true, length = 50)
    private String shippingAddressUsageIndicator;
    @Basic@Column(name = "shipping_address_usage_date", nullable = true, length = 50)
    private String shippingAddressUsageDate;
    @Basic@Column(name = "billing_address_address1", nullable = true, length = 50)
    private String billingAddressAddress1;
    @Basic@Column(name = "billing_address_address2", nullable = true, length = 50)
    private String billingAddressAddress2;
    @Basic@Column(name = "billing_address_address3", nullable = true, length = 50)
    private String billingAddressAddress3;
    @Basic@Column(name = "billing_address_address4", nullable = true, length = 50)
    private String billingAddressAddress4;
    @Basic@Column(name = "billing_address_address5", nullable = true, length = 50)
    private String billingAddressAddress5;
    @Basic@Column(name = "billing_address_suburb", nullable = true, length = 50)
    private String billingAddressSuburb;
    @Basic@Column(name = "billing_address_city", nullable = true, length = 50)
    private String billingAddressCity;
    @Basic@Column(name = "billing_address_postal_code", nullable = true, length = 50)
    private String billingAddressPostalCode;
    @Basic@Column(name = "billing_address_country", nullable = true, length = 50)
    private String billingAddressCountry;
    @Basic@Column(name = "billing_address_usage_indicator", nullable = true, length = 50)
    private String billingAddressUsageIndicator;
    @Basic@Column(name = "billing_address_usage_date", nullable = true, length = 50)
    private String billingAddressUsageDate;
    @Basic@Column(name = "initiation_failure_message", nullable = true, length = 2147483647)
    private String initiationFailureMessage;
    @Basic@Column(name = "transaction_uid", nullable = true, length = 50)
    private String transactionUid;
    @Basic@Column(name = "transaction_status", nullable = true, length = 50)
    private String transactionStatus;
    @Basic@Column(name = "transaction_initiated_date", nullable = true)
    private Timestamp transactionInitiatedDate;
    @Basic@Column(name = "transaction_completed_date", nullable = true)
    private Timestamp transactionCompletedDate;
    @Basic@Column(name = "transaction_card_holder", nullable = true, length = 50)
    private String transactionCardHolder;
    @Basic@Column(name = "transaction_card_number", nullable = true, length = 50)
    private String transactionCardNumber;
    @Basic@Column(name = "transaction_card_number_hash", nullable = true, length = 1000)
    private String transactionCardNumberHash;
    @Basic@Column(name = "transaction_card_type", nullable = true, length = 50)
    private String transactionCardType;
    @Basic@Column(name = "transaction_ip_address", nullable = true, length = 50)
    private String transactionIpAddress;
    @Basic@Column(name = "transaction_ip_country", nullable = true, length = 50)
    private String transactionIpCountry;
    @Basic@Column(name = "transaction_card_country", nullable = true, length = 50)
    private String transactionCardCountry;
    @Basic@Column(name = "transaction_three_d_secure_code", nullable = true, length = 50)
    private String transactionThreeDSecureCode;
    @Basic@Column(name = "transaction_three_d_secure_description", nullable = true, length = 50)
    private String transactionThreeDSecureDescription;
    @Basic@Column(name = "transaction_authorisation_code", nullable = true, length = 50)
    private String transactionAuthorisationCode;
    @Basic@Column(name = "transaction_bank_response_code", nullable = true, length = 50)
    private String transactionBankResponseCode;
    @Basic@Column(name = "transaction_response_code", nullable = true, length = 50)
    private String transactionResponseCode;
    @Basic@Column(name = "transaction_bank_response_message", nullable = true, length = 50)
    private String transactionBankResponseMessage;
    @Basic@Column(name = "transaction_amount", nullable = true, precision = 2)
    private BigDecimal transactionAmount;
    @Basic@Column(name = "transaction_authorised_amount", nullable = true, precision = 2)
    private BigDecimal transactionAuthorisedAmount;
    @Basic@Column(name = "transaction_settled_amount", nullable = true, precision = 2)
    private BigDecimal transactionSettledAmount;
    @Basic@Column(name = "transaction_refunded_amount", nullable = true, precision = 2)
    private BigDecimal transactionRefundedAmount;
    @ManyToOne@JoinColumn(name = "batch_id", referencedColumnName = "id", nullable = false)
    private BatchTransactionEntity batchTransactionByBatchId;

}
