package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "disbursement_config", schema = "dbo", catalog = "transaction_db")
public class DisbursementConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "api_key")
    private String apiKey;
    @Basic@Column(name = "access_token")
    private String accessToken;
    @Basic@Column(name = "secret")
    private String secret;
    @Basic@Column(name = "interface_id")
    private long interfaceId;

}
