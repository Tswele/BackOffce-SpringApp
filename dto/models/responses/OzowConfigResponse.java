package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;

@Data
public class OzowConfigResponse {
    String currencyCode;
    String countryCode;
    String siteCode;
    String bankReference;
    String privateKey;
    String apiKey;
}
