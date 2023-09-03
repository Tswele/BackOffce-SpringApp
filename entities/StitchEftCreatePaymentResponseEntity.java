package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "stitch_eft_create_payment_response", schema = "dbo", catalog = "transaction_db")
public class StitchEftCreatePaymentResponseEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "payment_id", nullable = false, length = -1)
    private String paymentId;
    @Basic@Column(name = "payment_url", nullable = false, length = -1)
    private String paymentUrl;
    @Basic@Column(name = "transaction_leg_id", nullable = false)
    private long transactionLegId;

}
