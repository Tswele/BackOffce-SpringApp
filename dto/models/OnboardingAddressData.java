package za.co.wirecard.channel.backoffice.dto.models;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OnboardingAddressData {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String postalCode;
    private long cityId;
    private boolean isPostal;
}
