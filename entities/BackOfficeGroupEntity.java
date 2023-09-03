package za.co.wirecard.channel.backoffice.entities;

import lombok.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateGroupRequest;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "back_office_group", schema = "dbo", catalog = "transaction_db")
public class BackOfficeGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "name")
    private String name;
    @Basic@Column(name = "description")
    private String description;
    @Basic@Column(name = "code")
    private String code;
    @Basic@Column(name = "last_modified")
    private Timestamp lastModified;
    @Basic@Column(name = "created_date")
    private Timestamp createdDate;
    @ManyToOne@JoinColumn(name = "created_by", referencedColumnName = "id")
    private BackOfficeUserEntity backOfficeUserByCreatedBy;
    @ManyToOne@JoinColumn(name = "modified_by", referencedColumnName = "id")
    private BackOfficeUserEntity backOfficeUserByModifiedBy;
    @OneToMany(mappedBy = "backOfficeGroupId")
    private Collection<BackOfficeUserEntity> backOfficeUserEntities;

    public BackOfficeGroupEntity(PlatformCreateGroupRequest group, BackOfficeUserEntity user) {
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
        this.code = group.getCode();
        this.lastModified = new Timestamp(Instant.now().toEpochMilli());
        this.createdDate = new Timestamp(Instant.now().toEpochMilli());
        this.backOfficeUserByCreatedBy = user;
        this.backOfficeUserByModifiedBy = user;
    }
//    @OneToMany(mappedBy = "backOfficeGroupByBackOfficeGroupId")
//    private Collection<BackOfficeGroupPermissionEntity> backOfficeGroupPermissionsById;
//    @OneToMany(mappedBy = "backOfficeGroupByBackOfficeGroupId")
//    private Collection<BackOfficeUserEntity> backOfficeUsersById;

}
