package za.co.wirecard.channel.backoffice.models.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.models.User;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest extends User {

    @NotNull
    private long groupId;
    @NotNull
    private long merchantId;
    @NotNull
    private long createdBy;
}
