package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.models.Group;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class BackOfficeDefaultGroup extends Group {
    @NotNull
    private long merchantId;
    @NotNull
    private long createdBy;
    private String description;

    public BackOfficeDefaultGroup(@NotNull long merchantId, @NotNull long createdBy, String description) {
        this.merchantId = merchantId;
        this.createdBy = createdBy;
        this.description = description;
    }
}
