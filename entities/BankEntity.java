package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@Table(name = "bank", schema = "dbo", catalog = "transaction_db")
public class BankEntity {
    @Basic@Column(name = "name")
    private String name;
    @Basic@Column(name = "last_modified")
    private Timestamp lastModified;
    @Id@Column(name = "id")
    private long id;
//    @OneToMany(mappedBy = "bankByBankId")
//    private Collection<BankBranchCodeEntity> bankBranchCodesById;

}
