package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cardinal_configuration", schema = "dbo", catalog = "transaction_db")
public class CardinalConfigurationEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "org_unit_id")
    private String orgUnitId;
    @Basic@Column(name = "card_configuration_id")
    private long cardConfigurationId;

}
