package za.co.wirecard.channel.backoffice.dto.models;


import lombok.*;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.models.Country;
import za.co.wirecard.channel.backoffice.models.MerchantClassification;
import za.co.wirecard.channel.backoffice.models.MerchantStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OnboardingFirstStep {
    private List<MerchantClassification> merchantClassificationList;
    private List<MerchantStatus> merchantStatusList;
    private List<Country> countryList;
    private List<BackOfficeUserEntity> accountManagers;
    private List<BackOfficeUserEntity> salesPersons;
}
