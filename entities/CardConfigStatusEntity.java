package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "card_config_status", schema = "dbo", catalog = "transaction_db")
public class CardConfigStatusEntity {

    @Basic@Column(name = "status")
    private String status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

}
