package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor

public class PlatformCreateGroupResponse {
    @NotNull
    public long groupId;
}
