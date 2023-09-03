package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDetails {

    private String cardHolderFullName;
    private String maskedCardNumber;
    private String cardType;
    private String eciCode;
    private String authorisationId;

}