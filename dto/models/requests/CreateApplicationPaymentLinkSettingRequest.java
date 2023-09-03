package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateApplicationPaymentLinkSettingRequest {
    @NotNull
    String applicationUid;
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
