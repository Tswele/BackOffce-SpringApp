package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "master_pass_config", schema = "dbo", catalog = "transaction_db")
public class MasterPassConfigEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "api_username")
    private String apiUsername;
    @Basic@Column(name = "api_key")
    private String apiKey;
    @Basic@Column(name = "interface_id")
    private long interfaceId;
    @Basic@Column(name = "notification_key")
    private String notificationKey;

}
