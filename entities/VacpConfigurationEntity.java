package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vacp_configuration", schema = "dbo", catalog = "transaction_db")
public class VacpConfigurationEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "username")
    private String username;
    @Basic@Column(name = "password")
    private String password;
    @Basic@Column(name = "partial_auth_indicator")
    private boolean partialAuthIndicator;
    @Basic@Column(name = "ignore_avs_result")
    private boolean ignoreAvsResult;
    @Basic@Column(name = "ignore_cv_result")
    private boolean ignoreCvResult;
    @Basic@Column(name = "card_configuration_id")
    private long cardConfigurationId;

}
