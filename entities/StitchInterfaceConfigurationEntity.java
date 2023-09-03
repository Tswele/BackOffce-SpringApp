package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "stitch_interface_configuration", schema = "dbo", catalog = "transaction_db")
public class StitchInterfaceConfigurationEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "interface_id", nullable = false)
    private long interfaceId;
    @Basic@Column(name = "bank_reference", nullable = false, length = 255)
    private String bankReference;

}
