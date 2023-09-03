package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "line_item_transaction", schema = "dbo", catalog = "transaction_db")
public class LineItemTransactionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "line_item_id", nullable = false)
    private long lineItemId;
    @Basic
    @Column(name = "sub_line_item_id", nullable = true)
    private Long subLineItemId;
    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id", nullable = false)
    private TransactionEntity transactionByTransactionId;

}
