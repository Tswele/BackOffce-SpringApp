package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.entities.CurrencyEntity;
import za.co.wirecard.channel.backoffice.repositories.CurrencyRepository;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger logger = LogManager.getLogger(CurrencyServiceImpl.class);

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<CurrencyEntity> getCurrencies() {
        return currencyRepository.findAll();
    }

}
