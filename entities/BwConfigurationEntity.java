package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bw_configuration", schema = "dbo", catalog = "transaction_db")
public class BwConfigurationEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "username")
    private String username;
    @Basic@Column(name = "password")
    private String password;
    @Basic@Column(name = "card_configuration_id")
    private long cardConfigurationId;
    @Basic@Column(name = "terminal_id")
    private String terminalId;

}
