package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateOzowConfigRequest {
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