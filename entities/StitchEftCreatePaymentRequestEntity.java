package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name = "stitch_eft_create_payment_request", schema = "dbo", catalog = "transaction_db")
public class StitchEftCreatePaymentRequestEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "amount", nullable = false, precision = 2)
    private BigDecimal amount;
    @Basic@Column(name = "currency", nullable = false, length = -1)
    private String currency;
    @Basic@Column(name = "payer_reference", nullable = false, length = -1)
    private String payerReference;
    @Basic@Column(name = "beneficiary_reference", nullable = false, length = -1)
    private String beneficiaryReference;
    @Basic@Column(name = "external_reference", nullable = false, length = -1)
    private String externalReference;
    @Basic@Column(name = "beneficiary_name", nullable = false, length = -1)
    private String beneficiaryName;
    @Basic@Column(name = "beneficiary_bank_id", nullable = false, length = -1)
    private String beneficiaryBankId;
    @Basic@Column(name = "beneficiary_account_number", nullable = false, length = -1)
    private String beneficiaryAccountNumber;
    @Basic@Column(name = "transaction_leg_id", nullable = false)
    private long transactionLegId;

}
