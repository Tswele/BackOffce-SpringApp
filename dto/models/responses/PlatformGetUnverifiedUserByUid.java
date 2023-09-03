package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlatformGetUnverifiedUserByUid {
    @NotNull
    private long id;
}
