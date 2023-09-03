package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateStitchConfigRequest {
    @NotNull
    long merchantId;
    @NotNull
    long applicationId;
    @NotNull
    String currencyCode;
    @NotNull
    String countryCode;
    @NotNull
    String bankReference;
    @NotNull
    long bankId;
    @NotNull
    String accountNumber;

}
