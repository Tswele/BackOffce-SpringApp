package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EditMobicredConfigRequest {
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
