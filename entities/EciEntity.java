package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "eci", schema = "dbo", catalog = "transaction_db")
public class EciEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "eci")
    private String eci;
    @Basic@Column(name = "description")
    private String description;
//    @OneToMany(mappedBy = "eciByEciId")
//    private Collection<InterfaceEciEntity> interfaceEcisById;

}
