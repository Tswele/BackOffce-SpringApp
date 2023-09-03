package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@Table(name = "back_office_crud_permission", schema = "dbo", catalog = "transaction_db")
public class BackOfficeCrudPermissionEntity {

    @Id@Column(name = "id")
    private long id;
    @ManyToOne@JoinColumn(name = "back_office_crud_id", referencedColumnName = "id", nullable = false)
    private CrudOperationsEntity crudOperationsByBackOfficeCrudId;
    @ManyToOne@JoinColumn(name = "back_office_permission_id", referencedColumnName = "id", nullable = false)
    private BackOfficePermissionEntity backOfficePermissionByBackOfficePermissionId;

}
