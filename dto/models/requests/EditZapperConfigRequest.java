package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditZapperConfigRequest {
    @NotNull
    long merchantId;
    @NotNull
    long applicationId;

    String merchantMid;
    @NotNull
    String currencyCode;
    @NotNull
    String countryCode;
    @NotNull
    String zapperMerchantId;
    @NotNull
    String siteId;
    @NotNull
    String siteApiKey;
    @NotNull
    String merchantApiKey;
}
