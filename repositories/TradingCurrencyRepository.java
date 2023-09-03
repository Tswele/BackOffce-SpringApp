package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CountryEntity;
import za.co.wirecard.channel.backoffice.entities.CurrencyEntity;
import za.co.wirecard.channel.backoffice.entities.TradingCurrencyEntity;

public interface TradingCurrencyRepository extends JpaRepository<TradingCurrencyEntity, Long> {
    TradingCurrencyEntity findByCurrencyByCurrencyIdAndCountryByCountryId(CurrencyEntity currencyEntity, CountryEntity countryEntity);
}
