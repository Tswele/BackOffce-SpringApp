package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "stitch_eft_banks", schema = "dbo", catalog = "transaction_db")
public class StitchEftBanksEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "name", nullable = false, length = -1)
    private String name;
    @Basic@Column(name = "bank_id", nullable = false, length = -1)
    private String bankId;

}
