package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformGetCityByMerchantId;
import za.co.wirecard.channel.backoffice.models.Address;
import za.co.wirecard.channel.backoffice.models.Merchant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformGetAddressByMerchantIdResponse extends Address {
    private long id;
    private long cityId;
    private long merchantId;
    private PlatformGetCityByMerchantId city;
    private Merchant merchantByMerchantId;
}

