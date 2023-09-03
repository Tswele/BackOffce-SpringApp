package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;

@Data
public class MasterpassConfigResponse {
    String merchantMid;
    String currencyCode;
    String countryCode;
    String apiUsername;
    String apiKey;
    String notificationKey;
}
