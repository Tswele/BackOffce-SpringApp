package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "planet_configuration", schema = "dbo", catalog = "transaction_db")
public class PlanetConfigurationEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "bank_username")
    private String bankUsername;
    @Basic@Column(name = "bank_password")
    private String bankPassword;
    @Basic@Column(name = "descriptor_company")
    private String descriptorCompany;
    @Basic@Column(name = "descriptor_info")
    private String descriptorInfo;
    @Basic@Column(name = "descriptor_country")
    private String descriptorCountry;
    @Basic@Column(name = "descriptor_state")
    private String descriptorState;
    @Basic@Column(name = "descriptor_city")
    private String descriptorCity;
    @Basic@Column(name = "threedsecure_ord_unit_id")
    private String threedsecureOrdUnitId;
    @Basic@Column(name = "terminal_id")
    private String terminalId;
    @Basic@Column(name = "card_configuration_id")
    private long cardConfigurationId;

}
