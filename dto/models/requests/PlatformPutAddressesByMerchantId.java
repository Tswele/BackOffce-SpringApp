package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformPutAddressesByMerchantId {

    private PlatformCreateAddressRequest residentialAddress;
    private PlatformCreateAddressRequest postalAddress;

}
