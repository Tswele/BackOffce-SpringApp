package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.services.CountryService;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/countries")
public class CountryController {

    private final CountryService countryService;

    private static final Logger logger = LogManager.getLogger(CountryController.class);

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("")
    public ResponseEntity<?> getCountries() {
        return ResponseEntity.ok(countryService.getCountries());
    }
    @GetMapping("/provinces/{countryId}")
    public ResponseEntity<?> getProvincesByCountryId(@PathVariable long countryId) {
        return ResponseEntity.ok(countryService.getProvinces(countryId));
    }
    @GetMapping("/cities/{provinceId}")
    public ResponseEntity<?> getCitiesByProvinceId(@PathVariable  long provinceId) {
        return ResponseEntity.ok(countryService.getCities(provinceId));
    }
}
