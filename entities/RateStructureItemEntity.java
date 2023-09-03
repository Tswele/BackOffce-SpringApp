package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rate_structure_item", schema = "dbo", catalog = "transaction_db")
public class RateStructureItemEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "rate_structure_version_id", nullable = false)
    private Long rateStructureVersionId;
    @ManyToOne@JoinColumn(name = "rate_structure_item_classification_id", referencedColumnName = "id", nullable = false)
    private RateStructureItemClassificationEntity rateStructureItemClassificationByRateStructureItemClassificationId;
    @ManyToOne@JoinColumn(name = "rate_structure_line_item_id", referencedColumnName = "id", nullable = false)
    private RateStructureLineItemEntity rateStructureLineItemByRateStructureLineItemId;

}
