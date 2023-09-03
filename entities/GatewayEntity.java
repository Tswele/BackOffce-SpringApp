package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "gateway", schema = "dbo", catalog = "transaction_db")
public class GatewayEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "code", nullable = false, length = 50)
    private String code;
    @Basic@Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic@Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;
    @Basic@Column(name = "institution_code", nullable = true, length = 50)
    private String institutionCode;
//    @ManyToOne@JoinColumn(name = "payment_type_id", referencedColumnName = "id", nullable = false)
//    private PaymentTypeEntity paymentTypeByPaymentTypeId;

}
