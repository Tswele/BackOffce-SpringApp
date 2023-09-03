package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformVerifyUser;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {

    private String username;
    private PlatformVerifyUser userVerify;

}
