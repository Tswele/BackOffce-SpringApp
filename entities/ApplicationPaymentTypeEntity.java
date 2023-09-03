package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "application_payment_type", schema = "dbo", catalog = "transaction_db")
public class ApplicationPaymentTypeEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @ManyToOne@JoinColumn(name = "application_id", referencedColumnName = "id", nullable = false)
    private ApplicationEntity applicationByApplicationId;
    @ManyToOne@JoinColumn(name = "payment_type_id", referencedColumnName = "id", nullable = false)
    private PaymentTypeEntity paymentTypeByPaymentTypeId;
    @ManyToOne@JoinColumn(name = "interface_id", referencedColumnName = "id", nullable = false)
    private InterfaceEntity interfaceByInterfaceId;
    @Basic@Column(name = "application_id",  insertable = false, updatable = false)
    private long applicationId;
    @Basic@Column(name = "payment_type_id",  insertable = false, updatable = false)
    private long paymentTypeId;
    @Basic@Column(name = "interface_id",  insertable = false, updatable = false)
    private long interfaceId;
    @Basic@Column(name = "is_active", nullable = false)
    private boolean isActive;

//    @Basic
//    @Column(name = "status_id", nullable = false)
//    private Long statusId;
    @ManyToOne@JoinColumn(name = "status_id", referencedColumnName = "id")
    private StatusEntity statusByStatusId;

    @Basic
    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Basic
    @Column(name = "activated_date")
    private Timestamp activatedDate;

    @Basic
    @Column(name = "cancelled_date")
    private Timestamp cancelledDate;

    @Basic
    @Column(name = "last_modified_by_id", nullable = true)
    private Long lastModifiedBy;

    @Basic
    @Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;

}
