package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@Table(name = "interface", schema = "dbo", catalog = "transaction_db")
public class InterfaceEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "merchant_id", insertable = false, updatable = false, nullable = false)
    private long merchantId;
    @Basic@Column(name = "name", nullable = false, length = 250)
    private String name;
    @Basic@Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic@Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;
    @Basic@Column(name = "merchant_mid", nullable = true, length = 250)
    private String merchantMid;
    @ManyToOne@JoinColumn(name = "gateway_id", referencedColumnName = "id", nullable = true)
    private GatewayEntity gatewayByGatewayId;
    @ManyToOne@JoinColumn(name = "trading_currency_id", referencedColumnName = "id", nullable = false)
    private TradingCurrencyEntity tradingCurrencyByTradingCurrencyId;
    @ManyToOne@JoinColumn(name = "payment_type_id", referencedColumnName = "id", nullable = false)
    private PaymentTypeEntity paymentTypeByPaymentTypeId;
    @ManyToOne@JoinColumn(name = "merchant_id", referencedColumnName = "id", nullable = false)
    private MerchantEntity merchantByMerchantId;
    @ManyToOne@JoinColumn(name = "security_method_id", referencedColumnName = "id", nullable = true)
    private SecurityMethodEntity securityMethodBySecurityMethodId;
    @Basic@Column(name = "payment_type_id", insertable = false, updatable = false)
    private long paymentTypeId;
    @Basic@Column(name = "gateway_id", insertable = false, updatable = false)
    private Long gatewayId;
    @Basic@Column(name = "trading_currency_id", insertable = false, updatable = false)
    private long tradingCurrencyId;
    @Basic@Column(name = "security_method_id", insertable = false, updatable = false)
    private Long securityMethodId;
    @Basic@Column(name = "payment_type_selection", nullable = true)
    private Boolean paymentTypeSelection;
    @ManyToOne@JoinColumn(name = "tds_merchant_type", referencedColumnName = "id")
    private TdsMerchantTypeEntity tdsMerchantTypeByTdsMerchantType;

//    @OneToMany(mappedBy = "interfaceByInterfaceId")
  //private Collection<InterfaceEciEntity> interfaceEcisById;

    @OneToMany(mappedBy = "interfaceByInterfaceId")
    private Collection<ApplicationPaymentTypeEntity> applicationPaymentTypesById;

    @Basic@Column(name = "fraud_enabled")
    private Boolean fraudEnabled;
    @Basic@Column(name = "card_version")
    private Integer cardVersion;

}
