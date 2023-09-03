package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BankservConfiguration {
    private String orgUnitId;
    private String merchantId;
    private String transactionType;
    private String threeDsPassword;
}
