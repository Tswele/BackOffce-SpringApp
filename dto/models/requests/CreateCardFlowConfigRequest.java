package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import za.co.wirecard.channel.backoffice.dto.models.GatewayProperties;
import za.co.wirecard.channel.backoffice.dto.models.TdsProperties;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardFlowConfigRequest {
    private String name;
    private String description;

    private String currencyCode;
    private String countryCode;

    private boolean onlyAuthenticatedToken;
    private boolean onlyVerifiedToken;
    private boolean tdsEnabled;
    private boolean cvvRequired;
    private boolean autoSettle;

    private boolean tokenizationEnabled;
    // private String cardFlow;
    private List<String> cardTypes;
    private List<String> tokenizationMethods;
    private String gateway;
    private String merchantType;
    private String tdsMethod;
    private List<String> ecis;
    private GatewayProperties gatewayProperties;
    private TdsProperties tdsProperties;

}
