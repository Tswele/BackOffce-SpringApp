package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "merchant_product", schema = "dbo", catalog = "transaction_db")
public class MerchantProductEntity {
    @Id@Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic@Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;
    @Basic@Column(name = "active", nullable = false)
    private Boolean active;
    @ManyToOne@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private ProductEntity productByProductId;
    @Basic@Column(name = "merchant_id", nullable = false)
    private Long merchantId;
    @Basic@Column(name = "last_modified_by", nullable = true)
    private Long lastModifiedBy;
    @Basic@Column(name = "date_added", nullable = true)
    private Timestamp dateAdded;
    @Basic@Column(name = "date_inactive", nullable = true)
    private Timestamp dateInactive;
    @Basic@Column(name = "custom_rate_structure", nullable = true)
    private Long customRateStructure;
    @Basic@Column(name = "custom_pricing_model", nullable = true)
    private Long customPricingModel;
    //    @Basic@Column(name = "product_id", nullable = false)
//    private Long productId;
    @Basic@Column(name = "selected_pricing_model_id", nullable = true)
    private Long selectedPricingModelId;

//    @Basic
//    @Column(name = "status_id", nullable = false)
//    private Long statusId;

    @ManyToOne@JoinColumn(name = "status_id", referencedColumnName = "id")
    private StatusEntity statusByStatusId;

    @Basic
    @Column(name = "activated_date")
    private Timestamp activatedDate;

    @Basic
    @Column(name = "cancelled_date")
    private Timestamp cancelledDate;

}
