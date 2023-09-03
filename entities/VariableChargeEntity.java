package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "variable_charge", schema = "dbo", catalog = "transaction_db")
public class VariableChargeEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "vatable", nullable = false)
    private Boolean vatable;
    @Basic@Column(name = "vat_inclusive", nullable = false)
    private Boolean vatInclusive;
    @Basic@Column(name = "from_amount", nullable = false)
    private Long fromAmount;
    @Basic@Column(name = "to_amount", nullable = false)
    private Long toAmount;
    @Basic@Column(name = "pricing_model_version_id", nullable = false)
    private Long pricingModelVersionId;
    @OneToMany(mappedBy = "variableChargeId")
    private Collection<VariableChargeRateStructureLineItemEntity> variableChargeRateStructureLineItemEntities;

}
