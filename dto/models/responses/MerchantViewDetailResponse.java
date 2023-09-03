package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.OnboardingSecondStepData;
import za.co.wirecard.channel.backoffice.entities.AuthUserEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantViewDetailResponse {

    private MerchantEntity merchantEntity;
    private MerchantAddressResponse physicalAddress;
    private MerchantAddressResponse postalAddress;
    private OnboardingSecondStepData billingDetail;
    private AuthUserEntity primaryContact;

}
