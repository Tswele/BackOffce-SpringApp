package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "product", schema = "dbo", catalog = "transaction_db")
public class ProductEntity {
    @Id@Column(name = "id", nullable = false)@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic@Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic@Column(name = "product_code", nullable = false, length = 100)
    private String productCode;
    @Basic@Column(name = "description", nullable = false, length = -1)
    private String description;
    @Basic@Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;
    @Basic@Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;
    @Basic@Column(name = "active", nullable = false)
    private Boolean active;
    @Basic@Column(name = "transaction_enabler", nullable = false)
    private Boolean transactionEnabler;
    @Basic@Column(name = "product_group_id", nullable = true)
    private Long productGroupId;
    @Basic@Column(name = "product_classification_id", nullable = true)
    private Long productClassificationId;
    @ManyToOne@JoinColumn(name = "product_sales_group_id", referencedColumnName = "id")
    private ProductSalesGroupEntity productSalesGroupByProductSalesGroupId;
//    @Basic@Column(name = "product_sales_group_id", nullable = true)
//    private Long productSalesGroupId;
    @Basic@Column(name = "last_modified_by", nullable = true)
    private Long lastModifiedBy;
    @Basic@Column(name = "pricing_model_id", nullable = true)
    private Long pricingModelId;
    @Basic@Column(name = "date_inactive", nullable = true)
    private Timestamp dateInactive;

}
