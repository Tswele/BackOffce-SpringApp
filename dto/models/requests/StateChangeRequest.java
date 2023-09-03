package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class StateChangeRequest {

    private Long id;
    private Timestamp requestedDate;
    private String levelCode;
    private String currentStateCode;
    //private int requestedStateId;
    private String requestedStateCode;
    private long requestedId;

}
