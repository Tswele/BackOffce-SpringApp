package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "session_data", schema = "dbo", catalog = "transaction_db")
public class SessionDataEntity {
    @Id@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "\"key\"", nullable = false, length = 4000)
    private String key;
    @Basic@Column(name = "val", nullable = true, length = 2147483647)
    private String val;
    @ManyToOne@JoinColumn(name = "session_id", referencedColumnName = "id", nullable = false)
    private SessionEntity sessionBySessionId;

}
