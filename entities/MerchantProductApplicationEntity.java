package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "merchant_product_application", schema = "dbo", catalog = "transaction_db")
public class MerchantProductApplicationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "selected_pricing_model", referencedColumnName = "id", nullable = true)
    private PricingModelEntity selectedPricingModel;
    @Basic
    @Column(name = "custom_pricing_model", nullable = true)
    private Long customPricingModel;
    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id", nullable = false)
    private ApplicationEntity applicationByApplicationId;
    @ManyToOne
    @JoinColumn(name = "merchant_product_id", referencedColumnName = "id", nullable = true)
    private MerchantProductEntity merchantProductByMerchantProductId;

}
