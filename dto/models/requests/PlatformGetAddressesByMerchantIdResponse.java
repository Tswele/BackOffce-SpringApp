package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformGetAddressByMerchantIdResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformGetAddressesByMerchantIdResponse {

    private PlatformGetAddressByMerchantIdResponse residentialAddress;
    private PlatformGetAddressByMerchantIdResponse postalAddress;

}
