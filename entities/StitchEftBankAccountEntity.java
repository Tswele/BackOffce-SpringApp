package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "stitch_eft_bank_account", schema = "dbo", catalog = "transaction_db")
public class StitchEftBankAccountEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "stitch_interface_configuration_id", nullable = false)
    private long stitchInterfaceConfigurationId;
    @Basic@Column(name = "bank_id", nullable = false)
    private long bankId;
    @Basic@Column(name = "bank_account_number", nullable = false, length = -1)
    private String bankAccountNumber;

}
