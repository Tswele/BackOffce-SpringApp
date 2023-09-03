package za.co.wirecard.channel.backoffice.dto.models;

import lombok.*;
import za.co.wirecard.channel.backoffice.dto.models.responses.MerchantAddressResponse;
import za.co.wirecard.channel.backoffice.entities.AuthUserEntity;
import za.co.wirecard.channel.backoffice.entities.BillingDetailEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantAddressEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;
import za.co.wirecard.channel.backoffice.models.BillingDetail;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncompleteOnboardingData {

    private MerchantEntity merchantEntity;
    private MerchantAddressResponse physicalAddress;
    private MerchantAddressResponse postalAddress;
    private OnboardingSecondStepData billingDetail;
    private AuthUserEntity primaryContact;
}
