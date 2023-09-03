package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditStitchConfigRequest {
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
    long bankId;
    @NotNull
    String accountNumber;
}