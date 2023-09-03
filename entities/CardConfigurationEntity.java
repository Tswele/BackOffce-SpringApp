package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "card_configuration", schema = "dbo", catalog = "transaction_db")
public class CardConfigurationEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "name")
    private String name;
    @Basic@Column(name = "description")
    private String description;
    @Basic@Column(name = "interface_id")
    private long interfaceId;
    @ManyToOne
    @JoinColumn(name = "interface_id", referencedColumnName = "id", insertable = false, updatable = false)
    private InterfaceEntity interfaceByInterfaceId;
    @Basic@Column(name = "tds_method_id")
    private long tdsMethodId;
    @Basic@Column(name = "gateway_id")
    private long gatewayId;
    @Basic@Column(name = "card_flow_id")
    private long cardFlowId;
    @ManyToOne
    @JoinColumn(name = "card_flow_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CardFlowEntity cardFlowByCardFlowId;
    @Basic@Column(name = "tokenization_enabled")
    private boolean tokenizationEnabled;
    @Basic@Column(name = "merchant_type_id")
    private long merchantTypeId;
    @Basic@Column(name = "only_authenticated_token")
    private boolean onlyAuthenticatedToken;
    @Basic@Column(name = "only_verified_token")
    private boolean onlyVerifiedToken;
    @Basic@Column(name = "tds_required")
    private boolean tdsRequired;
    @Basic@Column(name = "cvv_required")
    private boolean cvvRequired;
    @Basic@Column(name = "auto_settle")
    private boolean autoSettle;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private CardConfigStatusEntity cardConfigStatusByCardConfigStatusId;


//    @OneToMany(mappedBy = "cardConfigurationByCardConfigurationId")
//    private Collection<CardTypeGroupEntity> cardTypeGroupsByCardTypeGroupIds;
}
