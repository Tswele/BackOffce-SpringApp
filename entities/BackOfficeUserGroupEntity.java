package za.co.wirecard.channel.backoffice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateGroupRequest;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "back_office_group", schema = "dbo", catalog = "transaction_db")
public class BackOfficeUserGroupEntity {
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic@Column(name = "description")
    private String description;
    @Basic@Column(name = "code")
    private String code;
    @Basic@Column(name = "last_modified")
    private Timestamp lastModified;
    @Basic@Column(name = "created_date")
    private Timestamp createdDate;

}
