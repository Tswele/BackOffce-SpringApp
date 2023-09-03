package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "eft_interface_config", schema = "dbo", catalog = "transaction_db")
public class EftInterfaceConfigEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "interface_id")
    private long interfaceId;
    @Basic@Column(name = "merchant_key")
    private String merchantKey;
    @Basic@Column(name = "merchant_secret")
    private String merchantSecret;

}
