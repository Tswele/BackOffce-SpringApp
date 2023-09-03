package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import za.co.wirecard.channel.backoffice.entities.InterfaceEntity;


@Getter
@Setter
@NoArgsConstructor
public class PaymentInterface {

    private long id;
    private String name;
    private TradingCurrency tradingCurrency;

    public PaymentInterface (InterfaceEntity interfaceEntity){

        this.setId(interfaceEntity.getId());
        this.setName(interfaceEntity.getName());
        this.setTradingCurrency(new TradingCurrency(interfaceEntity.getTradingCurrencyByTradingCurrencyId()));

    }
}
