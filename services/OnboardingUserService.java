package za.co.wirecard.channel.backoffice.services;

import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.IncompleteOnboardingData;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateAuthUserResponse;
import za.co.wirecard.channel.backoffice.models.OnboardingAdminGroup;
import za.co.wirecard.channel.backoffice.models.OnboardingAdminGroupResponse;
import za.co.wirecard.channel.backoffice.models.requests.CreateAdminUserRequest;

@Service
public interface OnboardingUserService {

    OnboardingAdminGroupResponse createAdminGroup(OnboardingAdminGroup onboardingAdminGroup);

    PlatformCreateAuthUserResponse createAdminUser(CreateAdminUserRequest createAdminUserRequest);

    IncompleteOnboardingData getIncompleteOnboardingData(long merchantId);

    void updateMerchantToOnboarded(Long id);

    PlatformCreateAuthUserResponse editAdminUser(CreateAdminUserRequest createAdminUserRequest, long merchantId);
}
