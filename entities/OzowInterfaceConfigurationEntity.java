package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "ozow_interface_configuration", schema = "dbo", catalog = "transaction_db")
public class OzowInterfaceConfigurationEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "interface_id", nullable = false)
    private long interfaceId;
    @Basic@Column(name = "site_code", nullable = false, length = -1)
    private String siteCode;
    @Basic@Column(name = "bank_reference", nullable = false, length = -1)
    private String bankReference;
    @Basic@Column(name = "private_key", nullable = false, length = -1)
    private String privateKey;
    @Basic@Column(name = "api_key", nullable = false, length = -1)
    private String apiKey;

}
