package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.responses.Create2FAResponse;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.services.TwoFAService;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @NotNull
    private int userId;
    @NotNull
    private boolean twoFactorAuthEnabled;
    private Create2FAResponse create2FAResponse;

    public Login(int userId, BackOfficeUserEntity backOfficeUserEntity, TwoFAService twoFAService, Boolean reset) {
        this.userId = userId;
        this.twoFactorAuthEnabled = backOfficeUserEntity.getTwoFaEnabled() == null ? false : backOfficeUserEntity.getTwoFaEnabled();
        if (!twoFactorAuthEnabled || reset) {
            this.create2FAResponse = twoFAService.createTwoFactorAuth(this.userId, backOfficeUserEntity.getEmail());
        }
    }
}
