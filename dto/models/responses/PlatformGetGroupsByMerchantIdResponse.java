package za.co.wirecard.channel.backoffice.dto.models.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.models.Group;
import za.co.wirecard.channel.backoffice.models.User;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformGetGroupsByMerchantIdResponse {
    @NotNull
    private long id;
    private String name;
    private String code;
    private long createdBy;
    private long modifiedBy;
    private int numberOfUsers;
    private Date dateCreated;
    private Date lastModified;
}
