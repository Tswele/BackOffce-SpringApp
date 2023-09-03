package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;

@Data
public class WalletConfigResponse {
    String merchantMid;
    String currencyCode;
    String countryCode;
}
