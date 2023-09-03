package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "invoice", schema = "dbo", catalog = "transaction_db")
public class InvoiceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "invoice_number", nullable = false, length = 100)
    private String invoiceNumber;
    @Basic
    @Column(name = "from_date", nullable = false)
    private Timestamp fromDate;
    @Basic
    @Column(name = "to_date", nullable = false)
    private Timestamp toDate;
    @Basic
    @Column(name = "date_created", nullable = false)
    private Timestamp dateCreated;
    @Basic
    @Column(name = "calculation_string", nullable = false, length = 10000)
    private String calculationString;
    @ManyToOne
    @JoinColumn(name = "billing_run_id", referencedColumnName = "id", nullable = false)
    private BillingRunEntity billingRunByBillingRunId;
    @ManyToOne
    @JoinColumn(name = "invoice_type_id", referencedColumnName = "id", nullable = false)
    private InvoiceTypeEntity invoiceTypeByInvoiceTypeId;
    @ManyToOne
    @JoinColumn(name = "merchant_id", referencedColumnName = "id", nullable = false)
    private MerchantEntity merchantByMerchantId;

}
