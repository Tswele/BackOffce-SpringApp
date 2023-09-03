package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;

@Data
public class EftConfigResponse {
    String merchantMid;
    String currencyCode;
    String countryCode;
    String merchantKey;
    String merchantSecret;
}
