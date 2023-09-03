package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@Table(name = "card_type_group", schema = "dbo", catalog = "transaction_db")
public class CardTypeGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "card_type_id")
    private long cardTypeId;
    @ManyToOne
    @JoinColumn(name = "card_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CardTypeEntity cardTypeByCardTypeId;
    @Basic@Column(name = "card_configuration_id")
    private long cardConfigurationId;
    @ManyToOne
    @JoinColumn(name = "card_configuration_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CardConfigurationEntity cardConfigurationByCardConfigurationId;
    @ManyToOne
    @JoinColumn(name = "card_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CardTypeEntity cardTypeEntityByCardTypeId;
}
