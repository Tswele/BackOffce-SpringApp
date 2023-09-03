package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import za.co.wirecard.channel.backoffice.entities.TradingCurrencyEntity;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class TradingCurrency {

    private long id;
    private Date lastModified;
    private Country country;
    private Currency currency;

    public TradingCurrency (TradingCurrencyEntity tradingCurrencyEntity) {
        this.setId(tradingCurrencyEntity.getId());
        this.setLastModified(tradingCurrencyEntity.getLastModified());
        this.setCountry(new Country(tradingCurrencyEntity.getCountryByCountryId()));
        this.setCurrency(new Currency(tradingCurrencyEntity.getCurrencyByCurrencyId()));
        this.setCurrency(currency);
    }
}
