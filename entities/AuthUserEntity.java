package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Data
@ToString
@Table(name = "auth_user", schema = "dbo", catalog = "transaction_db")
public class AuthUserEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "first_name")
    private String firstName;
    @Basic@Column(name = "last_name")
    private String lastName;
    @Basic@Column(name = "known_as")
    private String knownAs;
    @Basic@Column(name = "email")
    private String email;
    @Basic@Column(name = "landline")
    private String landline;
    @Basic@Column(name = "cell")
    private String cell;
    @Basic@Column(name = "date_registered")
    private Timestamp dateRegistered;
    @Basic@Column(name = "last_modified")
    private Timestamp lastModified;
    @Basic@Column(name = "password")
    private String password;
    @Basic@Column(name = "birth_date")
    private Date birthDate;

}
