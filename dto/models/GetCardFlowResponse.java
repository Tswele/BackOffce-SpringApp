package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import za.co.wirecard.channel.backoffice.entities.CardConfigStatusEntity;

import java.util.List;

@Data
public class GetCardFlowResponse {

    private long id;

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

    private String cardFlow;
    private List<String> cardTypes;
    private List<String> tokenizationMethods;
    private String gateway;
    private String merchantType;
    private String tdsMethod;
    private List<String> ecis;
    private GatewayProperties gatewayProperties;
    private TdsProperties tdsProperties;

    private CardConfigStatusEntity status;

}
