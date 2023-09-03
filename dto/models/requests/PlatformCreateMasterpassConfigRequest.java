package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformCreateMasterpassConfigRequest {
    @NotNull
    String apiUsername;
    @NotNull
    String apiKey;
    @NotNull
    long interfaceId;
    String notificationKey;
}
