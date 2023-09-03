package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import za.co.wirecard.channel.backoffice.entities.CurrencyEntity;

import java.util.List;

@Data
public class CurrencyList {

    private List<CurrencyEntity> currencies;

    public CurrencyList(List<CurrencyEntity> currencies) {
        this.currencies = currencies;
    }
}
