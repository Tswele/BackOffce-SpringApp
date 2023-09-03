package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;

@Data
public class DisbursementConfigResponse {
    String merchantMid;
    String currencyCode;
    String countryCode;
    String apiKey;
    String accessToken;
    String secret;
}
