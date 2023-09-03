package za.co.wirecard.channel.backoffice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@Table(name = "rate_structure_version", schema = "dbo", catalog = "transaction_db")
public class RateStructureVersionEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "version", nullable = false)
    private Long version;
    @Basic@Column(name = "last_modified", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp lastModified;
    @Basic@Column(name = "rate_structure_id", nullable = false)
    private Long rateStructureId;
    @OneToMany(mappedBy = "rateStructureVersionId")
    private Collection<RateStructureItemEntity> rateStructureItemsById;
    @ManyToOne@JoinColumn(name = "last_modified_by", referencedColumnName = "id", nullable = false)
    private BackOfficeUserEntity backOfficeUserByLastModifiedBy;
//    @ManyToOne@JoinColumn(name = "billing_frequency_id", referencedColumnName = "id", nullable = false)
//    private BillingFrequencyEntity billingFrequencyByBillingFrequencyId;
    @ManyToOne@JoinColumn(name = "rate_structure_state_id", referencedColumnName = "id", nullable = false)
    private RateStructureStateEntity rateStructureStateByRateStructureStateId;

}
