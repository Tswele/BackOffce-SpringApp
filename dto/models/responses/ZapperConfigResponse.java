package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;

@Data
public class ZapperConfigResponse {
    String merchantMid;
    String currencyCode;
    String countryCode;
    String zapperMerchantId;
    String siteId;
    String siteApiKey;
    String merchantApiKey;
}
