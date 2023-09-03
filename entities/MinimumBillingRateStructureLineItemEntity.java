package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "minimum_billing_rate_structure_line_item", schema = "dbo", catalog = "transaction_db")
public class MinimumBillingRateStructureLineItemEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "minimum_billing_id", nullable = false)
    private Long minimumBillingId;
    @ManyToOne@JoinColumn(name = "rate_structure_line_item_id", referencedColumnName = "id", nullable = false)
    private RateStructureLineItemEntity rateStructureLineItemByRateStructureLineItemId;

}
