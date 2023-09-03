package za.co.wirecard.channel.backoffice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "merchant", schema = "dbo", catalog = "transaction_db")
public class MerchantEntity {
    @Id@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "company_name", nullable = false, length = 250)
    private String companyName;
    @Basic@Column(name = "trading_as", nullable = true, length = 250)
    private String tradingAs;
    @Basic@Column(name = "vat_number", nullable = true, length = 250)
    private String vatNumber;
    @Basic@Column(name = "merchant_uid", nullable = false, length = 250)
    private String merchantUid;
    @Basic@Column(name = "account_number", nullable = false, length = 50)
    private String accountNumber;
    @Basic@Column(name = "date_joined", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateJoined;
    @Basic@Column(name = "activation_date", nullable = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date activationDate;
    @Basic@Column(name = "company_reg_no", nullable = false, length = 50)
    private String companyRegNo;
    @Basic@Column(name = "invoice_day", nullable = true)
    private Integer invoiceDay;
    @Basic@Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;
    @Basic@Column(name = "cancelled_date", nullable = false)
    private Timestamp cancelledDate;
    private Boolean isDeleted;
    private Boolean isActive;
    @Basic@Column(name = "coda_number", nullable = true, length = 250)
    private String codaNumber;
    @Basic@Column(name = "sqlds_customer_id", nullable = true)
    private Integer sqldsCustomerId;
    @Basic@Column(name = "website", nullable = true, length = 255)
    private String website;
    @ManyToOne@JoinColumn(name = "created_by", referencedColumnName = "id")
    private BackOfficeUserEntity backOfficeUserByCreatedBy;
    @ManyToOne@JoinColumn(name = "modified_by", referencedColumnName = "id")
    private BackOfficeUserEntity backOfficeUserByModifiedBy;
    @ManyToOne@JoinColumn(name = "merchant_status_id", referencedColumnName = "id")
    private StatusEntity merchantStatusByMerchantStatusId;
    @ManyToOne@JoinColumn(name = "merchant_classification_id", referencedColumnName = "id")
    private MerchantClassificationEntity merchantClassificationByMerchantClassificationId;
    @ManyToOne@JoinColumn(name = "credit_controller_id", referencedColumnName = "id")
    private BackOfficeUserEntity backOfficeUserByCreditControllerId;
    @ManyToOne@JoinColumn(name = "sales_person_id", referencedColumnName = "id")
    private BackOfficeUserEntity backOfficeUserBySalesPersonId;
    @ManyToOne@JoinColumn(name = "account_manager_id", referencedColumnName = "id")
    private BackOfficeUserEntity backOfficeUserByAccountManagerId;
//    @Basic@Column(name = "created_by", nullable = true)
//    private Long createdBy;
//    @Basic@Column(name = "modified_by", nullable = true)
//    private Long modifiedBy;
//    @Basic@Column(name = "merchant_status_id", nullable = true)
//    private Long merchantStatusId;
//    @Basic@Column(name = "merchant_classification_id", nullable = true)
//    private Long merchantClassificationId;
//    @Basic@Column(name = "credit_controller_id", nullable = true)
//    private Long creditControllerId;
//    @Basic@Column(name = "sales_person_id", nullable = true)
//    private Long salesPersonId;
//    @Basic@Column(name = "account_manager_id", nullable = true, insertable = false)
//    private Long accountManagerId;
    @Basic@Column(name = "xero_contact_id", nullable = true, length = 250)
    private String xeroContactId;
    @Basic@Column(name = "billing_company_id", nullable = true)
    private Long billingCompanyId;
    @Basic@Column(name = "xero_tenant_id", nullable = true, length = 250)
    private String xeroTenantId;

    @Basic
    @Column(name = "is_deleted", nullable = false)
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "is_active", nullable = false)
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}
