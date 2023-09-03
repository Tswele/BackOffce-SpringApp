package za.co.wirecard.channel.backoffice.models.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SqlQuerySettlement {

    private long responseCodeId;
    private long merchantId;

}