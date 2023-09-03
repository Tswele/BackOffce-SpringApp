package za.co.wirecard.channel.backoffice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@Table(name = "pricing_model_version", schema = "dbo", catalog = "transaction_db")
public class PricingModelVersionEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "version", nullable = false)
    private Long version;
    @Basic@Column(name = "last_modified", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp lastModified;
    @Basic@Column(name = "pricing_model_id", nullable = false)
    private Long pricingModelId;
    @OneToMany(mappedBy = "pricingModelVersionId")
    private Collection<FixedChargeEntity> fixedChargesById;
    @OneToMany(mappedBy = "pricingModelVersionId")
    private Collection<MinimumBillingEntity> minimumBillingsById;
    @ManyToOne@JoinColumn(name = "last_modified_by", referencedColumnName = "id", nullable = false)
    private BackOfficeUserEntity backOfficeUserByLastModifiedBy;
    @OneToMany(mappedBy = "pricingModelVersionId")
    private Collection<VariableChargeEntity> variableChargesById;

}
