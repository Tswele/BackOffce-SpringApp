package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tds_method", schema = "dbo", catalog = "transaction_db")
public class TdsMethodEntity {
    @Id
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "name")
    private String name;
    @Basic@Column(name = "code")
    private String code;

}
