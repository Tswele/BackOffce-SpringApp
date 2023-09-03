package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MobicredConfigResponse {
    public String merchantMid;
    public String currencyCode;
    public String countryCode;
    public String cMerchantId;
    public String cMerchantKey;
}
