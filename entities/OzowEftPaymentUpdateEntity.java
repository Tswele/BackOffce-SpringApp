package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name = "ozow_eft_payment_update", schema = "dbo", catalog = "transaction_db")
public class OzowEftPaymentUpdateEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "transaction_leg_id", nullable = false)
    private long transactionLegId;
    @Basic@Column(name = "site_code", nullable = true, length = -1)
    private String siteCode;
    @Basic@Column(name = "transaction_id", nullable = true, length = -1)
    private String transactionId;
    @Basic@Column(name = "transaction_reference", nullable = true, length = -1)
    private String transactionReference;
    @Basic@Column(name = "amount", nullable = true, precision = 2)
    private BigDecimal amount;
    @Basic@Column(name = "status", nullable = true, length = -1)
    private String status;
    @Basic@Column(name = "currency_code", nullable = true, length = -1)
    private String currencyCode;
    @Basic@Column(name = "test", nullable = true)
    private Boolean test;
    @Basic@Column(name = "status_message", nullable = true, length = -1)
    private String statusMessage;

}
