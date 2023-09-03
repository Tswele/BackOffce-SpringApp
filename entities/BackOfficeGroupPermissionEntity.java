package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "back_office_group_permission", schema = "dbo", catalog = "transaction_db")
public class BackOfficeGroupPermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne@JoinColumn(name = "back_office_group_id", referencedColumnName = "id", nullable = false)
    private BackOfficeGroupEntity backOfficeGroupByBackOfficeGroupId;
    @ManyToOne@JoinColumn(name = "back_office_permission_id", referencedColumnName = "id", nullable = false)
    private BackOfficePermissionEntity backOfficePermissionByBackOfficePermissionId;

    public BackOfficeGroupPermissionEntity(BackOfficeGroupEntity backOfficeGroupEntity, BackOfficePermissionEntity backOfficePermissionEntity) {
        this.backOfficeGroupByBackOfficeGroupId = backOfficeGroupEntity;
        this.backOfficePermissionByBackOfficePermissionId = backOfficePermissionEntity;
    }

}
