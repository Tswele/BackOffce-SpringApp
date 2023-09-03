package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import za.co.wirecard.channel.backoffice.entities.CurrencyEntity;


@Getter
@Setter
@NoArgsConstructor
public class Currency {
    private long id;
    private String name;
    private String symbol;
    private String code;
    private String alphaCode;

    public Currency (CurrencyEntity currencyEntity){

        this.setId(currencyEntity.getId());
        this.setName(currencyEntity.getName());
        this.setSymbol(currencyEntity.getSymbol());
        this.setCode(currencyEntity.getCode());
        this.setAlphaCode(currencyEntity.getAlphaCode());

    }
}
