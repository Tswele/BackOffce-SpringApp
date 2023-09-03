package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@NoArgsConstructor
public class PlatformCreateGroupRequest {
    @NotNull
    private long id;
    private String name;
    private String code;
    private String description;
    private List<Long> permissions;
}
