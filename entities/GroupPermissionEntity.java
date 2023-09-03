/*package za.co.wirecard.channel.merchantportal.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "group_permission", schema = "dbo")
public class GroupPermissionEntity {
    private long id;
    private AuthGroupEntity authGroupByAuthGroupId;
    private AuthPermissionEntity authPermissionByAuthPermissionId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupPermissionEntity that = (GroupPermissionEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "auth_group_id", referencedColumnName = "id", nullable = false)
    public AuthGroupEntity getAuthGroupByAuthGroupId() {
        return authGroupByAuthGroupId;
    }

    public void setAuthGroupByAuthGroupId(AuthGroupEntity authGroupByAuthGroupId) {
        this.authGroupByAuthGroupId = authGroupByAuthGroupId;
    }

    @ManyToOne
    @JoinColumn(name = "auth_permission_id", referencedColumnName = "id", nullable = false)
    public AuthPermissionEntity getAuthPermissionByAuthPermissionId() {
        return authPermissionByAuthPermissionId;
    }

    public void setAuthPermissionByAuthPermissionId(AuthPermissionEntity authPermissionByAuthPermissionId) {
        this.authPermissionByAuthPermissionId = authPermissionByAuthPermissionId;
    }
}
*/