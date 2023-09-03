package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import za.co.wirecard.channel.backoffice.entities.SessionDataEntity;
import za.co.wirecard.channel.backoffice.entities.SessionEntity;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Session {
    private String sessionToken;
    private String merchantReference;
    private BigDecimal amount;
    private String merchantUid;
    private String applicationUid;
    private String currencyCode;
//    private String msisdn;
//    private String email;
    private String redirectSuccessfulUrl;
    private String redirectFailedUrl;
//    private String webhookSentKey;
//    private String virtualInitializationKey;
//    private String sessionConfigurationKey;
//    private List<SessionDataEntity> sessionDataEntityList;

    public Session(SessionEntity sessionEntity) {
        this.sessionToken = sessionEntity.getSessionToken();
        this.merchantReference = sessionEntity.getMerchantReference();
        this.amount = sessionEntity.getAmount();
        this.merchantUid = sessionEntity.getMerchantUid();
        this.applicationUid = sessionEntity.getApplicationUid();
        this.currencyCode = sessionEntity.getCurrencyCode();
        this.redirectSuccessfulUrl = sessionEntity.getRedirectSuccessfulUrl();
        this.redirectFailedUrl = sessionEntity.getRedirectFailedUrl();
//        this.sessionDataEntityList = sessionDataEntityList;

//        for (SessionDataEntity sessionDataEntity :
//                sessionDataEntityList) {
//            switch(sessionDataEntity.getKey()) {
//                case "email":
//                    this.email = sessionDataEntity.getKey();
//                    break;
//                case "msisdn":
//                    this.msisdn = sessionDataEntity.getKey();
//                    break;
//                case "WEBHOOK_SENT_KEY":
//                    this.webhookSentKey = sessionDataEntity.getKey();
//                    break;
//                case "VIRTUAL_INITIALIZATION_KEY":
//                    this.virtualInitializationKey = sessionDataEntity.getKey();
//                    break;
//                case "SESSION_CONFIGURATION_KEY":
//                    this.sessionConfigurationKey = sessionDataEntity.getKey();
//                    break;
//                default:
//                    break;
//
//            }
//        }
    }
}
