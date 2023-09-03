package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.models.ZapperConfig;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PlatformCreateZapperConfigRequest extends ZapperConfig {
    @NotNull
    long interfaceId;
}
