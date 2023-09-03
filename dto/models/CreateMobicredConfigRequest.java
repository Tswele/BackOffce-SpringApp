package za.co.wirecard.channel.backoffice.dto.models;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateMobicredConfigRequest {
    @NotNull
    long merchantId;
    @NotNull
    long applicationId;

    public String merchantMid;
    @NotNull
    public String currencyCode;
    @NotNull
    public String countryCode;
    @NotNull
    public String cMerchantId;
    @NotNull
    public String cMerchantKey;
}
