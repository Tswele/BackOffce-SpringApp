/*package za.co.wirecard.channel.merchantportal.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "auth_group", schema = "dbo")
public class AuthGroupEntity {
    private long id;
    private String name;
    private String description;
    private String code;
    private Timestamp lastModified;
    private Timestamp createdDate;
    private MerchantEntity merchantByMerchantId;
    private AuthUserEntity authUserByCreatedBy;
    private AuthUserEntity authUserByModifiedBy;
    private Collection<AuthUserEntity> authUsersById;
    private Collection<AuthPermissionEntity> groupPermissionsById;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 2147483647)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "code", nullable = false, length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "last_modified", nullable = false)
    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    @Basic
    @Column(name = "created_date", nullable = false)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthGroupEntity that = (AuthGroupEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(code, that.code) &&
                Objects.equals(lastModified, that.lastModified) &&
                Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, code, lastModified, createdDate);
    }

    @ManyToOne
    @JoinColumn(name = "merchant_id", referencedColumnName = "id", nullable = false)
    public MerchantEntity getMerchantByMerchantId() {
        return merchantByMerchantId;
    }

    public void setMerchantByMerchantId(MerchantEntity merchantByMerchantId) {
        this.merchantByMerchantId = merchantByMerchantId;
    }

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    public AuthUserEntity getAuthUserByCreatedBy() {
        return authUserByCreatedBy;
    }

    public void setAuthUserByCreatedBy(AuthUserEntity authUserByCreatedBy) {
        this.authUserByCreatedBy = authUserByCreatedBy;
    }

    @ManyToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    public AuthUserEntity getAuthUserByModifiedBy() {
        return authUserByModifiedBy;
    }

    public void setAuthUserByModifiedBy(AuthUserEntity authUserByModifiedBy) {
        this.authUserByModifiedBy = authUserByModifiedBy;
    }

    @OneToMany(mappedBy = "authGroupByAuthGroupId")
    public Collection<AuthUserEntity> getAuthUsersById() {
        return authUsersById;
    }

    public void setAuthUsersById(Collection<AuthUserEntity> authUsersById) {
        this.authUsersById = authUsersById;
    }

    @ManyToMany
    @JoinTable(name = "group_permission",
            joinColumns = @JoinColumn(name = "auth_group_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_permission_id"))
    public Collection<AuthPermissionEntity> getGroupPermissionsById() {
        return groupPermissionsById;
    }

    public void setGroupPermissionsById(Collection<AuthPermissionEntity> groupPermissionsById) {
        this.groupPermissionsById = groupPermissionsById;
    }
}
*/