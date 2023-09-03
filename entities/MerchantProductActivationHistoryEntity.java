package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "merchant_product_activation_history", schema = "dbo", catalog = "transaction_db")
public class MerchantProductActivationHistoryEntity {
    @Id@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;
    @Basic@Column(name = "merchant_product_activation_history_action_id", nullable = false)
    private Long merchantProductActivationHistoryActionId;
    @Basic@Column(name = "merchant_product_id", nullable = false)
    private Long merchantProductId;

}
