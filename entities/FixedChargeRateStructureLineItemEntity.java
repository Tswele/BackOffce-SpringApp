package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "fixed_charge_rate_structure_line_item", schema = "dbo", catalog = "transaction_db")
public class FixedChargeRateStructureLineItemEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "value", nullable = false, precision = 2)
    private BigDecimal value;
    @Basic@Column(name = "fixed_charge_id", nullable = false)
    private Long fixedChargeId;
    @ManyToOne@JoinColumn(name = "rate_structure_line_item_id", referencedColumnName = "id", nullable = false)
    private RateStructureLineItemEntity rateStructureLineItemByRateStructureLineItemId;

}
