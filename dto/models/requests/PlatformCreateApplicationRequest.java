package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.models.Application;
import za.co.wirecard.channel.backoffice.models.ConfigurationType;
import za.co.wirecard.channel.backoffice.models.SecurityType;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class PlatformCreateApplicationRequest extends Application {
    @NotNull
    private long merchantId;
    private List<SecurityType> securityTypes;
    private List<ConfigurationType> configurationTypes;
}
