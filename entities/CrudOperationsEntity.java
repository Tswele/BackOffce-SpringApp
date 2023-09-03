package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@ToString
@Table(name = "crud_operations", schema = "dbo", catalog = "transaction_db")
public class CrudOperationsEntity {
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
//    @OneToMany(mappedBy = "crudOperationsByCrudOperationsId")
//    private Collection<BackOfficeRoutePermissionEntity> backOfficeRoutePermissionsById;

//    private Collection<BackOfficeCrudPermissionEntity> backOfficeCrudPermissionsById;
//
//    @OneToMany(mappedBy = "crudOperationsByBackOfficeCrudId")
//    public Collection<BackOfficeCrudPermissionEntity> getBackOfficeCrudPermissionsById() {
//        return backOfficeCrudPermissionsById;
//    }
//
//    public void setBackOfficeCrudPermissionsById(Collection<BackOfficeCrudPermissionEntity> backOfficeCrudPermissionsById) {
//        this.backOfficeCrudPermissionsById = backOfficeCrudPermissionsById;
//    }
}
