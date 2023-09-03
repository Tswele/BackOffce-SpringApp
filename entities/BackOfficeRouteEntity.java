package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@ToString
@Table(name = "back_office_route", schema = "dbo", catalog = "transaction_db")
public class BackOfficeRouteEntity {
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

//    @OneToMany(mappedBy = "backOfficeRouteEntity")
//    private Collection<BackOfficePermissionEntity> backOfficePermissionEntities;
//    @OneToMany(mappedBy = "backOfficeRouteByBackOfficeRouteId")
//    private Collection<BackOfficeRoutePermissionEntity> backOfficeRoutePermissionsById;

//    @OneToMany(mappedBy = "backOfficeRouteByBackOfficeRouteId")
//    private Collection<BackOfficePermissionEntity> backOfficePermissionsById;

}
