package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "card_configuration_tokenization_method", schema = "dbo", catalog = "transaction_db")
public class CardConfigurationTokenizationMethodEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "card_configuration_id")
    private long cardConfigurationId;
    @Basic@Column(name = "tokenization_method_id")
    private long tokenizationMethodId;
    @ManyToOne
    @JoinColumn(name = "tokenization_method_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TokenizationMethodEntity tokenizationMethodByTokenizationMethodId;

}
