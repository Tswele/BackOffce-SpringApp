package za.co.wirecard.channel.backoffice.entities;/*package za.co.wirecard.channel.merchantportal.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "auth_permission", schema = "dbo")
public class AuthPermissionEntity {
    private long id;
    private String name;
    private String description;
    private String code;
    private Timestamp lastModified;
    private Collection<GroupPermissionEntity> groupPermissionsById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthPermissionEntity that = (AuthPermissionEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(code, that.code) &&
                Objects.equals(lastModified, that.lastModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, code, lastModified);
    }

    @OneToMany(mappedBy = "authPermissionByAuthPermissionId")
    public Collection<GroupPermissionEntity> getGroupPermissionsById() {
        return groupPermissionsById;
    }

    public void setGroupPermissionsById(Collection<GroupPermissionEntity> groupPermissionsById) {
        this.groupPermissionsById = groupPermissionsById;
    }
}
*/