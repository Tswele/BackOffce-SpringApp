package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class PlatformUpdateGroupRequest {
    @NotNull
    private long id;
    private String name;
    private String code;
    private String description;
    private List<Long> permissions;

}
