package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@ToString
@Table(name = "back_office_permission", schema = "dbo", catalog = "transaction_db")
public class BackOfficePermissionEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "name")
    private String name;
    @Basic@Column(name = "description")
    private String description;
    @Basic@Column(name = "code")
    private String code;
    @Basic@Column(name = "last_modified")
    private Timestamp lastModified;
    @ManyToOne@JoinColumn(name = "back_office_route_id", referencedColumnName = "id")
    private BackOfficeRouteEntity backOfficeRouteEntity;
    @OneToMany(mappedBy = "backOfficePermissionByBackOfficePermissionId")
    private Collection<BackOfficeCrudPermissionEntity> backOfficeCrudPermissionsById;

    //    @OneToMany(mappedBy = "backOfficePermissionByBackOfficePermissionId")
//    private Collection<BackOfficeAssociatedPermissionEntity> backOfficeAssociatedPermissionsById;
//    @OneToMany(mappedBy = "backOfficePermissionByBackOfficeAssociatedPermissionId")
//    private Collection<BackOfficeAssociatedPermissionEntity> backOfficeAssociatedPermissionsById_0;
//    @OneToMany(mappedBy = "backOfficePermissionByBackOfficePermissionId")
//    private Collection<BackOfficeGroupPermissionEntity> backOfficeGroupPermissionsById;
//
//    @OneToMany(mappedBy = "backOfficePermissionByBackOfficePermissionId")
//    private Collection<BackOfficeRoutePermissionEntity> backOfficeRoutePermissionsById;

}
