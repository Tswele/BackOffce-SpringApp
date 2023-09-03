package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@Table(name = "line_item", schema = "dbo", catalog = "transaction_db")
public class LineItemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "name", nullable = false, length = 250)
    private String name;
    @Basic
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    @Basic
    @Column(name = "unit_price", nullable = true, precision = 2)
    private BigDecimal unitPrice;
    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id", nullable = false)
    private InvoiceEntity invoiceByInvoiceId;
    @OneToMany(mappedBy = "lineItemId")
    private Collection<SubLineItemEntity> subLineItemsById;
    @OneToMany(mappedBy = "lineItemId")
    private Collection<LineItemTransactionEntity> lineItemTransactionsById;

}
