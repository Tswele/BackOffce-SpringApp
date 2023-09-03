package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bankserv_configuration", schema = "dbo", catalog = "transaction_db")
public class BankservConfigurationEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "org_unit_id")
    private String orgUnitId;
    @Basic@Column(name = "merchant_id")
    private String merchantId;
    @Basic@Column(name = "transaction_type")
    private String transactionType;
    @Basic@Column(name = "three_ds_password")
    private String threeDsPassword;
    @Basic@Column(name = "card_configuration_id")
    private long cardConfigurationId;

}
