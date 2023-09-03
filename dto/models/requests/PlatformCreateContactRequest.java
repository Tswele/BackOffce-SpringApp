package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.models.Contact;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor

public class PlatformCreateContactRequest extends Contact {
    @NotNull
    private long groupId;
    @NotNull
    private long merchantId;
    private Long createdBy;
}

