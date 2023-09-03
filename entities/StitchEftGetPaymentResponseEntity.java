package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "stitch_eft_get_payment_response", schema = "dbo", catalog = "transaction_db")
public class StitchEftGetPaymentResponseEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "payment_id", nullable = true, length = -1)
    private String paymentId;
    @Basic@Column(name = "payment_url", nullable = true, length = -1)
    private String paymentUrl;
    @Basic@Column(name = "user_reference", nullable = true, length = -1)
    private String userReference;
    @Basic@Column(name = "state", nullable = true, length = -1)
    private String state;
    @Basic@Column(name = "payment_date", nullable = true)
    private Timestamp paymentDate;
    @Basic@Column(name = "amount", nullable = true, precision = 2)
    private BigDecimal amount;
    @Basic@Column(name = "currency", nullable = true, length = -1)
    private String currency;
    @Basic@Column(name = "payer_account_number", nullable = true, length = -1)
    private String payerAccountNumber;
    @Basic@Column(name = "payer_bank_id", nullable = true, length = -1)
    private String payerBankId;
    @Basic@Column(name = "beneficiary_bank_id", nullable = true, length = -1)
    private String beneficiaryBankId;
    @Basic@Column(name = "transaction_leg_id", nullable = false)
    private long transactionLegId;

}
