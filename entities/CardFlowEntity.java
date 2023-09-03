package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@Table(name = "card_flow", schema = "dbo", catalog = "transaction_db")
public class CardFlowEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "name")
    private String name;
    @Basic@Column(name = "code")
    private String code;
    @Basic@Column(name = "description")
    private String description;

}
