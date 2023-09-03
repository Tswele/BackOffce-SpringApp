package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "merchant_document", schema = "dbo", catalog = "transaction_db")
public class MerchantDocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "link")
    private String link;
    @Basic@Column(name = "name")
    private String name;
    @ManyToOne@JoinColumn(name = "merchant_id", referencedColumnName = "id", nullable = false)
    private MerchantEntity merchantByMerchantId;

}
