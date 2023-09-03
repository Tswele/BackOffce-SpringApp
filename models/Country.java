package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import za.co.wirecard.channel.backoffice.entities.CountryEntity;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Country {
    private long id;
    private String alphaCode;
    private String code;
    private String name;
    private long isoNo;

    public Country(CountryEntity countryEntity) {
        this.setId(countryEntity.getId());
        this.setAlphaCode(countryEntity.getAlphaCode());
        this.setCode(countryEntity.getCode());
        this.setName(countryEntity.getName());
        this.setIsoNo(countryEntity.getIsoNo());
    }
}
