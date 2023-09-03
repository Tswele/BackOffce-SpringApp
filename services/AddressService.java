package za.co.wirecard.channel.backoffice.services;

import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateAddressRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformGetAddressesByMerchantIdResponse;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformPutAddressesByMerchantId;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateAddressResponse;
import za.co.wirecard.channel.backoffice.exceptions.MerchantAddressException;

@Service
public interface AddressService {

    PlatformGetAddressesByMerchantIdResponse getAddress(long merchantId) throws MerchantAddressException;

    PlatformCreateAddressResponse createAddress(PlatformCreateAddressRequest address) throws MerchantAddressException;

    void editAddress(PlatformPutAddressesByMerchantId address, long merchantId) throws MerchantAddressException;

    void deleteAddress(long merchantId) throws MerchantAddressException;

    PlatformCreateAddressResponse editAddressOnboarding(PlatformCreateAddressRequest platformCreateAddressRequest1, long merchantId);
}
