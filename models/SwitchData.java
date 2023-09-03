package za.co.wirecard.channel.backoffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class SwitchData {
    private Long LogID;
    private String priority;
    private Long transactionId;
    private String msg;
    private String request;
    private Timestamp timestamp;
}
