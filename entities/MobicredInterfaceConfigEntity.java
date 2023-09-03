package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mobicred_interface_config", schema = "dbo", catalog = "transaction_db")
public class MobicredInterfaceConfigEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "interface_id")
    private long interfaceId;
    @Basic@Column(name = "c_merchant_id")
    private String cMerchantId;
    @Basic@Column(name = "c_merchant_key")
    private String cMerchantKey;

}
