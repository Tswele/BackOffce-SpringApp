package za.co.wirecard.channel.backoffice.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "payment_type_state", schema = "dbo", catalog = "transaction_db")
public class PaymentStateEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "payment_type_id", nullable = false)
    private long paymentTypeId;
    @Basic@Column(name = "transaction_state_id", nullable = false)
    private long transactionStateId;
}
