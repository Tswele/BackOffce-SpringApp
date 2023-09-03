package za.co.wirecard.channel.backoffice.services;

import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.entities.CurrencyEntity;
import za.co.wirecard.channel.backoffice.repositories.CurrencyRepository;

import java.util.List;

@Service
public interface CurrencyService {

    List<CurrencyEntity> getCurrencies();

}
