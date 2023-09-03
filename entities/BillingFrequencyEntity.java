package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "billing_frequency", schema = "dbo", catalog = "transaction_db")
public class BillingFrequencyEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "name", nullable = false, length = 250)
    private String name;
    @Basic@Column(name = "description", nullable = false, length = 500)
    private String description;
    @Basic@Column(name = "code", nullable = false, length = 50)
    private String code;

}
