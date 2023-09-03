package za.co.wirecard.channel.backoffice.dto.models;


import lombok.*;
import za.co.wirecard.channel.backoffice.models.OnboardingAdminGroup;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OnboardingThirdStepData {
    private String firstName;
    private String lastName;
    private String knownAs;
    private String email;
    private String landline;
    private String cell;
    private String position;
    private Date birthDate;
    private Long merchantId;
    private Long createdBy;
//    private OnboardingAdminGroup onboardingAdminGroup;
}
