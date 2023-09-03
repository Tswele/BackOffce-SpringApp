package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "application_jwt_setting", schema = "dbo", catalog = "transaction_db")
public class ApplicationJwtSettingEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id")
    private long id;
    @Basic@Column(name = "secret_key")
    private String secretKey;
    @Basic@Column(name = "last_modified")
    private Timestamp lastModified;
    @Basic@Column(name = "application_security_setting_id")
    private long applicationSecuritySettingId;
    @Basic@Column(name = "inbound_enabled")
    private Boolean inboundEnabled;

}
