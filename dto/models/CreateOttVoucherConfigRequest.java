package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateOttVoucherConfigRequest {
    @NotNull
    long merchantId;
    @NotNull
    long applicationId;

    String merchantMid;
    @NotNull
    String currencyCode;
    @NotNull
    String countryCode;
}
