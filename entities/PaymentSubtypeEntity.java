package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "payment_subtype", schema = "dbo", catalog = "transaction_db")
public class PaymentSubtypeEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "external_id")
    private Long externalId;
    @Basic@Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic@Column(name = "refund_supported")
    private Boolean refundSupported;

}
