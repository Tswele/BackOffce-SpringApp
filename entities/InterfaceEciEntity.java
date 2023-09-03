package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "interface_eci", schema = "dbo", catalog = "transaction_db")
public class InterfaceEciEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "description")
    private String description;
    @ManyToOne@JoinColumn(name = "eci_id", referencedColumnName = "id", nullable = false)
    private EciEntity eciByEciId;

    @ManyToOne@JoinColumn(name = "interface_id", referencedColumnName = "id", nullable = false)
    private InterfaceEntity interfaceByInterfaceId;

}
