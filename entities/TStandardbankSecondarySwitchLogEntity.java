package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tabsa_dev_switch_log", schema = "public")
public class TStandardbankSecondarySwitchLogEntity {
    @Id@Column(name = "logid", nullable = false)
    private Long logId;
    @Basic@Column(name = "logger", nullable = true, length = 4000)
    private String logger;
    @Basic@Column(name = "priority", nullable = true, length = 50)
    private String priority;
    @Basic@Column(name = "loc_threadname", nullable = true, length = 4000)
    private String locThreadName;
    @Basic@Column(name = "loc_className", nullable = true, length = 4000)
    private String locClassName;
    @Basic@Column(name = "loc_methodName", nullable = true, length = 4000)
    private String locMethodName;
    @Basic@Column(name = "loc_filename", nullable = true, length = 4000)
    private String locFileName;
    @Basic@Column(name = "loc_linenumber", nullable = true, length = 4000)
    private String locLineNumber;
    @Basic@Column(name = "transactionid", nullable = true)
    private Long transactionId;
    @Basic@Column(name = "msg", nullable = true, length = 4000)
    private String msg;
    @Basic@Column(name = "request", nullable = true, length = 4000)
    private String request;
    @Basic@Column(name = "throwable", nullable = true, length = 4000)
    private String throwable;
    @Basic@Column(name = "timestamp", nullable = true)
    private Timestamp timestamp;

}
