package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZapperConfig {
    @NotNull
    String merchantId;
    @NotNull
    String siteId;
    @NotNull
    String siteApiKey;
    @NotNull
    String merchantApiKey;
}
