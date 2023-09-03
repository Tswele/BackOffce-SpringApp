package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "card_transaction", schema = "dbo", catalog = "transaction_db")
public class CardTransactionEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "card_number_hash", nullable = true, length = 2147483647)
    private String cardNumberHash;
    @Basic@Column(name = "card_number", nullable = false, length = 2147483647)
    private String cardNumber;
    @Basic@Column(name = "card_expiry_month", nullable = false)
    private short cardExpiryMonth;
    @Basic@Column(name = "card_expiry_year", nullable = false)
    private short cardExpiryYear;
    @Basic@Column(name = "cardholder_fullname", nullable = false, length = 50)
    private String cardholderFullname;
    @Basic@Column(name = "card_bin", nullable = false, length = 6)
    private String cardBin;
    @Basic@Column(name = "card_last_four", nullable = false, length = 4)
    private String cardLastFour;
    @Basic@Column(name = "budget_period", nullable = false)
    private short budgetPeriod;
    @Basic@Column(name = "card_type", nullable = false, length = 50)
    private String cardType;
    @Basic@Column(name = "issuing_bank", nullable = false, length = 50)
    private String issuingBank;
    @Basic@Column(name = "card_country", nullable = true, length = 50)
    private String cardCountry;
    @Basic@Column(name = "auto_settle", nullable = true)
    private Boolean autoSettle;
    @Basic@Column(name = "ip_address", nullable = true, length = 100)
    private String ipAddress;
    @Basic@Column(name = "unique_client_identifier", nullable = true, length = 400)
    private String uniqueClientIdentifier;
    @Basic@Column(name = "bank_error_code", nullable = true, length = 400)
    private String bankErrorCode;
    @Basic@Column(name = "bank_error_description", nullable = true, length = 2147483647)
    private String bankErrorDescription;
    @Basic@Column(name = "authorisation_id", nullable = true, length = 20)
    private String authorisationId;
    @ManyToOne@JoinColumn(name = "transaction_id", referencedColumnName = "id", nullable = false)
    private TransactionEntity transactionByTransactionId;
    @Basic@Column(name = "transaction_id", insertable = false, updatable = false)
    private long transactionId;
}
