package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.wirecard.channel.backoffice.dto.models.CurrencyList;
import za.co.wirecard.channel.backoffice.services.CurrencyService;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    private static final Logger logger = LogManager.getLogger(CurrencyController.class);

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("")
    public ResponseEntity<?> getCurrencies() {
        return ResponseEntity.ok(new CurrencyList(currencyService.getCurrencies()));
    }
}
