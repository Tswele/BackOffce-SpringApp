package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;

@Data
public class OttVoucherConfigResponse {
    String merchantMid;
    String currencyCode;
    String countryCode;
}
