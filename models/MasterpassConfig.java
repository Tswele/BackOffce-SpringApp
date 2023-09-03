package za.co.wirecard.channel.backoffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
public class MasterpassConfig {
    @NotNull
    String apiUsername;
    @NotNull
    String apiKey;
    String notificationKey;
}
