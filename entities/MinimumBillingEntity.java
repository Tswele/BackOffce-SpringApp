package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@Entity
@Table(name = "minimum_billing", schema = "dbo", catalog = "transaction_db")
public class MinimumBillingEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "vatable", nullable = false)
    private Boolean vatable;
    @Basic@Column(name = "vat_inclusive", nullable = false)
    private Boolean vatInclusive;
    @Basic@Column(name = "value", nullable = false, precision = 2)
    private BigDecimal value;
    @Basic@Column(name = "pricing_model_version_id", nullable = false)
    private Long pricingModelVersionId;
    @OneToMany(mappedBy = "minimumBillingId")
    private Collection<MinimumBillingRateStructureLineItemEntity> minimumBillingRateStructureLineItemEntities;

}
