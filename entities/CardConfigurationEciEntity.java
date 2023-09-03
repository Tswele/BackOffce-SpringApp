package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "card_configuration_eci", schema = "dbo", catalog = "transaction_db")
public class CardConfigurationEciEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "eci_id")
    private long eciId;
    @ManyToOne
    @JoinColumn(name = "eci_id", referencedColumnName = "id", insertable = false, updatable = false)
    private EciEntity eciByEciId;
    @Basic@Column(name = "card_configuration_id")
    private long cardConfigurationId;
}
