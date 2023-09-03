package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class PlatformGetGroups {
    private List<PlatformGetGroupsByMerchantIdResponse> groups;

    public PlatformGetGroups() {
        this.groups = new ArrayList<>();
    }
}

