package za.co.wirecard.channel.backoffice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import za.co.wirecard.channel.backoffice.models.TokenVerify;

@Component
@Scope("sesion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VerifyUserSession {

    private TokenVerify tokenVerify;

}
