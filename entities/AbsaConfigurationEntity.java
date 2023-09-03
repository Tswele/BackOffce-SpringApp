package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "absa_configuration", schema = "dbo", catalog = "transaction_db")
public class AbsaConfigurationEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "merchant_mid")
    private String merchantMid;
    @Basic@Column(name = "merchant_category_code")
    private String merchantCategoryCode;
    @Basic@Column(name = "terminal_id")
    private String terminalId;
    @Basic@Column(name = "card_configuration_id")
    private long cardConfigurationId;

}
