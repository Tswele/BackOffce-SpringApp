package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "merchant_classification", schema = "dbo", catalog = "transaction_db")
public class MerchantClassificationEntity {
    @Basic@Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic@Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;
    @Id@Column(name = "id", nullable = false)
    private Long id;

}
