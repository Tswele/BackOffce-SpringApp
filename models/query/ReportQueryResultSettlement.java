package za.co.wirecard.channel.backoffice.models.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportQueryResultSettlement {

    private String Date;
    private String Settled_Count;
    private String Settled;
    private String Refunded_Count;
    private String Refunded;
    private String Net_Settlement;

}
