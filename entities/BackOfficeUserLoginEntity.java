package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "back_office_user_login", schema = "dbo", catalog = "transaction_db")
public class BackOfficeUserLoginEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "ip_address")
    private String ipAddress;
    @Basic@Column(name = "login_attempt_count")
    private short loginAttemptCount;
    @Basic@Column(name = "latest_attempt_date")
    private Timestamp latestAttemptDate;
    @ManyToOne@JoinColumn(name = "back_office_user_id", referencedColumnName = "id", nullable = false)
    private BackOfficeUserEntity backOfficeUserByBackOfficeUserId;

}
