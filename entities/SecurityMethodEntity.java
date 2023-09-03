package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "security_method", schema = "dbo", catalog = "transaction_db")
public class SecurityMethodEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic@Column(name = "description", nullable = false, length = 2147483647)
    private String description;
    @Basic@Column(name = "code", nullable = false, length = 50)
    private String code;
    @Basic@Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;

}
