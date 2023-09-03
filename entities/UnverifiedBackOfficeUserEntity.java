package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "unverified_back_office_user", schema = "dbo", catalog = "transaction_db")
public class UnverifiedBackOfficeUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "uuid")
    private String uuid;
    @Basic@Column(name = "created")
    private Timestamp created;
    @ManyToOne@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private BackOfficeUserEntity backOfficeUserByUserId;

    public UnverifiedBackOfficeUserEntity(BackOfficeUserEntity backOfficeUserEntity) {
        this.uuid = UUID.randomUUID().toString();
        this.created = new Timestamp(Instant.now().toEpochMilli());
        this.backOfficeUserByUserId = backOfficeUserEntity;
    }
}
