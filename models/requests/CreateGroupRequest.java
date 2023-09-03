package za.co.wirecard.channel.backoffice.models.requests;

import lombok.*;
import za.co.wirecard.channel.backoffice.models.Group;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupRequest {

    @NotNull
    private long id;
    private String name;
    private String code;
    @NotNull
    private long createdBy;
    @NotNull
    private long modifiedBy;
    @NotNull
    private Long merchantId;
    private String description;
    private List<Long> permissions;

}
