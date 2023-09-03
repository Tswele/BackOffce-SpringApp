package za.co.wirecard.channel.backoffice.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "state_change_request", schema = "dbo", catalog = "transaction_db")
public class StateChangeRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "request_date", nullable = false)
    private Timestamp requestedDate;

    @Basic
    @Column(name = "level_id", nullable = false)
    private Long level;

    @Basic
    @Column(name = "current_state_id", nullable = false)
    private Long currentStateId;

    @Basic
    @Column(name = "requested_state_id", nullable = false)
    private Long requestedStateId;

    @Basic
    @Column(name = "requested_id", nullable = false)
    private Long requestedId;

    @Basic
    @Column(name = "requested_by_id", nullable = false)
    private long backOfficeUserId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StateChangeRequestEntity that = (StateChangeRequestEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
