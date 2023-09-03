package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacpConfiguration {
    private String username;
    private String password;
    private boolean partialAuthIndicator;
    private boolean ignoreAvsResult;
    private boolean ignoreCvResult;
}
