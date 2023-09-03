package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "zapper_interface_config", schema = "dbo", catalog = "transaction_db")
public class ZapperInterfaceConfigEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "interface_id")
    private long interfaceId;
    @Basic@Column(name = "merchant_id")
    private String merchantId;
    @Basic@Column(name = "site_id")
    private String siteId;
    @Basic@Column(name = "site_api_key")
    private String siteApiKey;
    @Basic@Column(name = "merchant_api_key")
    private String merchantApiKey;

}
