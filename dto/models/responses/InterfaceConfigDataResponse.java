package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.CountryEntity;
import za.co.wirecard.channel.backoffice.entities.CurrencyEntity;
import za.co.wirecard.channel.backoffice.models.Country;
import za.co.wirecard.channel.backoffice.models.Currency;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceConfigDataResponse {

    private List<Country> countries;
    private List<Currency> currencies;

}
