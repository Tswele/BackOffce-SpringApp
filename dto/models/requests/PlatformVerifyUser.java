package za.co.wirecard.channel.backoffice.dto.models.requests;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PlatformVerifyUser {
    @NotNull
    private String password;
}
