package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformGetCountryByMerchantId {

    private long id;
    private String alphaCode;
    private String code;
    private String name;
    private int isoNo;

}
