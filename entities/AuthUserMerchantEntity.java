package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "auth_user_merchant", schema = "dbo", catalog = "transaction_db")
public class AuthUserMerchantEntity {

    @Id@Column(name = "id")
    private long id;
    private boolean isActive;
    private boolean isDeleted;
    @Basic@Column(name = "primary_contact")
    private boolean primaryContact;
    @Basic@Column(name = "created_by")
    private Long createdBy;
    @Basic@Column(name = "modified_by")
    private Long modifiedBy;
    @Basic@Column(name = "position")
    private String position;
    private boolean isContact;
    @Basic@Column(name = "auth_user_id")
    private long authUserId;
    @Basic@Column(name = "merchant_id")
    private long merchantId;
    @Basic@Column(name = "auth_group_id")
    private Long authGroupId;
//    @OneToMany(mappedBy = "authUserMerchantByAuthUserMerchantId")
//    private Collection<AuthUserAuditEntity> authUserAuditsById;

    @Basic
    @Column(name = "is_active")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "is_deleted")
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "is_contact")
    public boolean isContact() {
        return isContact;
    }

    public void setContact(boolean contact) {
        isContact = contact;
    }

}
