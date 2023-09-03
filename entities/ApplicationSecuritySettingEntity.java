package za.co.wirecard.channel.backoffice.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "application_security_setting", schema = "dbo")
public class ApplicationSecuritySettingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "application_id", nullable = false)
    private long applicationId;

    @Basic
    @Column(name = "security_type_id", nullable = false)
    private long securityTypeId;

    @Basic
    @Column(name = "is_active")
    private Boolean isActive;

}
