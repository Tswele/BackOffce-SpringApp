package za.co.wirecard.channel.backoffice.dto.models;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OnboardingFirstStepData {
    private OnboardingMerchantData onboardingMerchantData;
    private OnboardingAddressData physicalAddressData;
    private OnboardingAddressData postalAddressData;
}
