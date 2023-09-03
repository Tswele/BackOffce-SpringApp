package za.co.wirecard.channel.backoffice.models.cache;

import lombok.*;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import za.co.wirecard.channel.backoffice.models.Token;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenCache extends JdkSerializationRedisSerializer implements Serializable {
    @NonNull
    private Token token;
    private int backOfficeUserId;
}
