package za.co.wirecard.channel.backoffice.dto.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.TStandardbankSecondarySwitchLogEntity;
import za.co.wirecard.channel.backoffice.models.SwitchData;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SwitchLog {
    private Long LogID;
    private String priority;
    private Long transactionId;
    private String msg;
    private String request;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public SwitchLog(SwitchData switchData) {
        this.LogID = switchData.getLogID();
        this.priority = switchData.getPriority();
        this.transactionId = switchData.getTransactionId();
        this.msg = switchData.getMsg();
        this.request = switchData.getRequest();
        this.timestamp = switchData.getTimestamp().toLocalDateTime();
    }
}
