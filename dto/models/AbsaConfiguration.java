package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbsaConfiguration {
    private String merchantMid;
    private String merchantCategoryCode;
    private String terminalId;
}
