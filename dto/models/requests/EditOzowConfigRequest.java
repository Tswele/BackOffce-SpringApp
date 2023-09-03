package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditOzowConfigRequest {
    @NotNull
    long merchantId;
    @NotNull
    long applicationId;
    @NotNull
    String currencyCode;
    @NotNull
    String countryCode;
    @NotNull
    String siteCode;
    @NotNull
    String bankReference;
    @NotNull
    String privateKey;
    @NotNull
    String apiKey;
}