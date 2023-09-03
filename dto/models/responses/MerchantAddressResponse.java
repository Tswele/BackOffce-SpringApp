package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.CityEntity;
import za.co.wirecard.channel.backoffice.entities.CountryEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantAddressEntity;
import za.co.wirecard.channel.backoffice.entities.ProvinceEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantAddressResponse {

    private long id;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String postalCode;
    private long cityId;
    private long merchantId;
    private CountryEntity countryEntity;
    private ProvinceEntity provinceEntity;
    private CityEntity city;
    private boolean postalAddress;

    public MerchantAddressResponse(MerchantAddressEntity merchantAddressEntity) {
        this.id = merchantAddressEntity.getId();
        this.addressLine1 = merchantAddressEntity.getAddressLine1();
        this.addressLine2 = merchantAddressEntity.getAddressLine2();
        this.addressLine3 = merchantAddressEntity.getAddressLine3();
        this.addressLine4 = merchantAddressEntity.getAddressLine4();
        this.city = merchantAddressEntity.getCity();
        this.cityId = merchantAddressEntity.getCityId();
        this.merchantId = merchantAddressEntity.getMerchantId();
        this.postalCode = merchantAddressEntity.getPostalCode();
        this.provinceEntity = merchantAddressEntity.getCity().getProvince();
        this.countryEntity = this.provinceEntity.getCountry();
        this.postalAddress = merchantAddressEntity.isPostalAddress();
    }

}
