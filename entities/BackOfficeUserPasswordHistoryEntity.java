package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "back_office_user_password_history", schema = "dbo", catalog = "transaction_db")
public class BackOfficeUserPasswordHistoryEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "created_date")
    private Timestamp createdDate;
    @Basic@Column(name = "password")
    private String password;
    @ManyToOne@JoinColumn(name = "back_office_user_id", referencedColumnName = "id", nullable = false)
    private BackOfficeUserEntity backOfficeUserByBackOfficeUserId;

}
