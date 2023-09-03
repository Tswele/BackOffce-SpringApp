package za.co.wirecard.channel.backoffice.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@Table(name = "application", schema = "dbo", catalog = "transaction_db")
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;
    @Basic
    @Column(name = "application_uid", nullable = false, length = 255)
    private String applicationUid;
    @ManyToOne@JoinColumn(name = "merchant_id", referencedColumnName = "id", nullable = false)
    private MerchantEntity merchantByMerchantId;
    @Basic
    @Column(name = "merchant_id", insertable = false, updatable = false, nullable = false)
    private long merchantId;
    @Basic
    @Column(name = "auto_settle", nullable = false)
    private boolean autoSettle;
    @OneToMany(mappedBy = "applicationByApplicationId")
    private Collection<MerchantProductApplicationEntity> merchantProductApplicationsById;

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
    @Column(name = "is_active")
    private boolean isActive;

    @Basic@Column(name = "last_modified_by_id", nullable = true)
    private Long lastModifiedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public String getApplicationUid() {
        return applicationUid;
    }

    public void setApplicationUid(String applicationUid) {
        this.applicationUid = applicationUid;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public boolean isAutoSettle() {
        return autoSettle;
    }

    public void setAutoSettle(boolean autoSettle) {
        this.autoSettle = autoSettle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationEntity that = (ApplicationEntity) o;
        return id == that.id && merchantId == that.merchantId && autoSettle == that.autoSettle && Objects.equals(name, that.name) && Objects.equals(lastModified, that.lastModified) && Objects.equals(applicationUid, that.applicationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, merchantId, name, lastModified, applicationUid, autoSettle);
    }

    public Collection<MerchantProductApplicationEntity> getMerchantProductApplicationsById() {
        return merchantProductApplicationsById;
    }

    public void setMerchantProductApplicationsById(Collection<MerchantProductApplicationEntity> merchantProductApplicationsById) {
        this.merchantProductApplicationsById = merchantProductApplicationsById;
    }
}
