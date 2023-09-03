package za.co.wirecard.channel.backoffice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "bank_account_type", schema = "dbo", catalog = "transaction_db")
public class BankAccountTypeEntity {

    @Id
    @Basic
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;

}
