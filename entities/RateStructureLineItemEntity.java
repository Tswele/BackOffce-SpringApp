package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rate_structure_line_item", schema = "dbo", catalog = "transaction_db")
public class RateStructureLineItemEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "name", nullable = false, length = 250)
    private String name;
    @Basic@Column(name = "description", nullable = false, length = 500)
    private String description;
    @Basic@Column(name = "code", nullable = false, length = 50)
    private String code;
    @ManyToOne@JoinColumn(name = "transaction_action_id", referencedColumnName = "id", nullable = true)
    private TransactionActionEntity transactionActionByTransactionActionId;
    @Basic@Column(name = "isfixedcharge", nullable = true)
    private Boolean isfixedcharge;
    @ManyToOne@JoinColumn(name = "billing_frequency_id", referencedColumnName = "id", nullable = true)
    private BillingFrequencyEntity billingFrequencyByBillingFrequencyId;

}
