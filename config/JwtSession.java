package za.co.wirecard.channel.backoffice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import za.co.wirecard.channel.backoffice.models.Token;

@Component
@Scope("session")
@Data
public class JwtSession {

    private Token token;

}
