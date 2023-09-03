package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformGetCityByMerchantId {

    private long id;
    private String name;
    private String code;
    private long provinceId;
    private PlatformGetProvinceByMerchantId province;

}
