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
@Table(name = "level", schema = "dbo", catalog = "transaction_db")
public class LevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic@Column(name = "name", nullable = false, length = 255)
    private String name;

    @Basic@Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;

    @Basic@Column(name = "code", nullable = false)
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LevelEntity that = (LevelEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
