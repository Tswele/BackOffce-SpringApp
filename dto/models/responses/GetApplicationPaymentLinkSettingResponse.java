package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;

@Data
public class GetApplicationPaymentLinkSettingResponse {
    Long id;
    String backgroundColour;
    String header;
    String logoImage;
    String accentColour;
    String buttonColour;
    String buttonTextColour;
    String message;
    Boolean reSendPayment;
    Boolean allowPartialPayment;
    Long timePeriod;
}
