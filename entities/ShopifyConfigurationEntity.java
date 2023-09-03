package za.co.wirecard.channel.backoffice.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shopify_configuration", schema = "dbo", catalog = "transaction_db")
public class ShopifyConfigurationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "shop_domain", nullable = false, length = 1000)
    private String shopDomain;
    @Basic
    @Column(name = "access_token", nullable = true, length = 1000)
    private String accessToken;
    @Basic
    @Column(name = "nonce", nullable = true, length = 1000)
    private String nonce;
    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id", nullable = false)
    private ApplicationEntity applicationByApplicationId;

}
