package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "security_type", schema = "dbo", catalog = "transaction_db")
public class SecurityTypeEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "name")
    private String name;
    @Basic@Column(name = "description")
    private String description;
    @Basic@Column(name = "code")
    private String code;
    @Basic@Column(name = "last_modified")
    private Timestamp lastModified;

}
