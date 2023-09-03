package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@Table(name = "product_payment_type", schema = "dbo", catalog = "transaction_db")
public class ProductPaymentTypeEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", updatable = false, insertable = false, nullable = false)
    private ProductEntity productEntityByProductId;
    @ManyToOne
    @JoinColumn(name = "payment_type_id", referencedColumnName = "id", updatable = false, insertable = false, nullable = false)
    private PaymentTypeEntity paymentTypeEntityByPaymentTypeId;

}
