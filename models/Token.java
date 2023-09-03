package za.co.wirecard.channel.backoffice.models;

import lombok.*;

import java.util.List;

@Data
public class Token {

    private String access_token;
//    private String token_type;
//    private String refresh_token;
//    private int expires_in;
//    private String scope;
    private String firstName;
//    private String lastName;
    private int userId;
//    private int merchantId;
//    private String userGroup;
    private List<Authority> permissions;
//    private String jti;
    private List<RouteConfig> routeConfig;

}
