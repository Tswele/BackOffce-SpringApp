package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "bank_branch_code", schema = "dbo", catalog = "transaction_db")
public class BankBranchCodeEntity {
    @Basic@Column(name = "code")
    private String code;
    @Basic@Column(name = "last_modified")
    private Timestamp lastModified;
    @Id@Column(name = "id")
    private long id;
    @ManyToOne@JoinColumn(name = "bank_id", referencedColumnName = "id", nullable = false)
    private BankEntity bankByBankId;

}
