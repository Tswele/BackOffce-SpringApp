package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "back_office_associated_permission", schema = "dbo", catalog = "transaction_db")
public class BackOfficeAssociatedPermissionEntity {
    @Id@Column(name = "id")
    private long id;
    @ManyToOne@JoinColumn(name = "back_office_permission_id", referencedColumnName = "id", nullable = false)
    private BackOfficePermissionEntity backOfficePermissionByBackOfficePermissionId;
    @ManyToOne@JoinColumn(name = "back_office_associated_permission_id", referencedColumnName = "id", nullable = false)
    private BackOfficePermissionEntity backOfficePermissionByBackOfficeAssociatedPermissionId;

}
