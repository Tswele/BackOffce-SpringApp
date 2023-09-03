package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class TokenVerify {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;
    private String jti;
    private long userId;
}
