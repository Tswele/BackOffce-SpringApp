package za.co.wirecard.channel.backoffice.dto.models;


import lombok.*;
import za.co.wirecard.channel.backoffice.models.MerchantClassification;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OnboardingMerchantData {
    private String companyName;
    private String tradingAs;
    private String companyRegNo;
    private String vatNo;
    private long companyTypeId;
    private long merchantClassificationId;
    private String website;
    private Long merchantStatusId;
    private long accountManagerId;
    private long salesPersonId;
}
