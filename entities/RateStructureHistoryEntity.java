package za.co.wirecard.channel.backoffice.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rate_structure_history", schema = "dbo", catalog = "transaction_db")
public class RateStructureHistoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "rate_structure_name", nullable = false, length = 250)
    private String rateStructureName;
    @Basic
    @Column(name = "rate_structure_version", nullable = false)
    private Long rateStructureVersion;
    @Basic
    @Column(name = "pricing_model_name", nullable = false, length = 250)
    private String pricingModelName;
    @Basic
    @Column(name = "pricing_model_version", nullable = false)
    private Long pricingModelVersion;
    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id", nullable = false)
    private InvoiceEntity invoiceByInvoiceId;
}
