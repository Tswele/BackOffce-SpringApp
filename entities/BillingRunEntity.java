package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "billing_run", schema = "dbo", catalog = "transaction_db")
public class BillingRunEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "name", nullable = true, length = 250)
    private String name;
    @Basic
    @Column(name = "date_requested", nullable = false)
    private Timestamp dateRequested;
    @Basic
    @Column(name = "date_approved", nullable = true)
    private Timestamp dateApproved;
    @Basic
    @Column(name = "from_date", nullable = false)
    private Timestamp fromDate;
    @Basic
    @Column(name = "to_date", nullable = false)
    private Timestamp toDate;
    @ManyToOne
    @JoinColumn(name = "requested_by", referencedColumnName = "id", nullable = false)
    private BackOfficeUserEntity backOfficeUserByRequestedBy;
    @ManyToOne
    @JoinColumn(name = "approved_by", referencedColumnName = "id")
    private BackOfficeUserEntity backOfficeUserByApprovedBy;
    @ManyToOne
    @JoinColumn(name = "billing_run_status_id", referencedColumnName = "id", nullable = false)
    private BillingRunStatusEntity billingRunStatusByBillingRunStatusId;

}
