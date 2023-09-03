package za.co.wirecard.channel.backoffice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import za.co.wirecard.channel.backoffice.config.Utils;
import za.co.wirecard.channel.backoffice.dto.models.UserRole;
import za.co.wirecard.channel.backoffice.models.User;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "back_office_user", schema = "dbo", catalog = "transaction_db")
public class BackOfficeUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "back_office_group_id")
    private long backOfficeGroupId;
    @Basic@Column(name = "first_name", nullable = false, length = 250)
    private String firstName;
    @Basic@Column(name = "last_name", nullable = false, length = 250)
    private String lastName;
    @Basic@Column(name = "known_as", nullable = true, length = 250)
    private String knownAs;
    @Basic@Column(name = "email", nullable = false, length = 250)
    private String email;
    @Basic@Column(name = "landline", nullable = true, length = 15)
    private String landline;
    @Basic@Column(name = "cell", nullable = false, length = 15)
    private String cell;
    @Basic@Column(name = "date_registered", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp dateRegistered;
    @Basic@Column(name = "last_modified", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp lastModified;
    @Basic@Column(name = "password", nullable = true, length = 250)
    private String password;
    private Boolean isDeleted;
    private Boolean isActive;
    @Basic@Column(name = "birth_date", nullable = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp birthDate;
    @Basic@Column(name = "position", nullable = false, length = 50)
    private String position;
    @Basic
    @Column(name = "is_account_manager", nullable = true)
    private Boolean isAccountManager;
    @Basic
    @Column(name = "is_sales_person", nullable = true)
    private Boolean isSalesPerson;
    @Basic
    @Column(name = "is_credit_controller", nullable = true)
    private Boolean isCreditController;
    @Basic@Column(name = "secret_key", nullable = true, length = 2147483647)
    private String secretKey;
    @Basic@Column(name = "two_fa_enabled", nullable = true)
    private Boolean twoFaEnabled;
    @Basic@Column(name = "otp", nullable = true, length = 5)
    private String otp;
    @ManyToOne@JoinColumn(name = "back_office_group_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BackOfficeUserGroupEntity group;
    @Basic@Column(name = "created_by")
    private Long createdBy;
    @Basic@Column(name = "modified_by", nullable = true)
    private Long modifiedBy;
    @Basic@Column(name = "last_login_date", nullable = true)
    private Timestamp lastLoginDate;
    private Boolean isBlocked;

    public BackOfficeUserEntity(User user, long userId) {
        if (user.getId() != null && StringUtils.isNotBlank(user.getId().toString())) {
            this.id = user.getId();
        }
        this.backOfficeGroupId = user.getGroupId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.knownAs = user.getFirstName() + user.getLastName();
        this.email = user.getEmail();
        this.landline = user.getLandline();
        this.cell = user.getCell();
        this.dateRegistered = new Timestamp(Instant.now().toEpochMilli());
        this.lastModified = new Timestamp(Instant.now().toEpochMilli());
        this.isDeleted = false;
        this.isActive = false;
        this.createdBy = userId;
        // this.modifiedBy = null;
        if (user.getBirthDate() != null) {
            this.birthDate = new Timestamp(user.getBirthDate().getTime());
        }
        this.position = user.getPosition();
        for (UserRole userRole: user.getUserRoles().getUserRoles()) {
            if (userRole.getCode().equalsIgnoreCase(Utils.BACK_OFFICE_ROLE_ACCOUNT_MANAGER)) {
                this.isAccountManager = true;
            } else if (userRole.getCode().equalsIgnoreCase(Utils.BACK_OFFICE_ROLE_CREDIT_CONTROLLER)) {
                this.isCreditController = true;
            } else if (userRole.getCode().equalsIgnoreCase(Utils.BACK_OFFICE_ROLE_SALES_PERSON)) {
                this.isSalesPerson = true;
            }
        }
        this.twoFaEnabled = false;
    }

    @Basic
    @Column(name = "is_account_manager", nullable = true)
    public Boolean getAccountManager() {
        return isAccountManager;
    }

    public void setAccountManager(Boolean accountManager) {
        isAccountManager = accountManager;
    }

    @Basic
    @Column(name = "is_sales_person", nullable = true)
    public Boolean getSalesPerson() {
        return isSalesPerson;
    }

    public void setSalesPerson(Boolean salesPerson) {
        isSalesPerson = salesPerson;
    }

    @Basic
    @Column(name = "is_credit_controller", nullable = true)
    public Boolean getCreditController() {
        return isCreditController;
    }

    public void setCreditController(Boolean creditController) {
        isCreditController = creditController;
    }

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

    @Basic
    @Column(name = "is_blocked", nullable = true)
    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

}
