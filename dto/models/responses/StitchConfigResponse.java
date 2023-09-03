package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;
import za.co.wirecard.channel.backoffice.entities.StitchEftBanksEntity;

@Data
public class StitchConfigResponse {
    String currencyCode;
    String countryCode;
    String bankReference;
    StitchEftBanksEntity bank;
    String accountNumber;
}
