package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import za.co.wirecard.channel.backoffice.config.UtilityService;
import za.co.wirecard.channel.backoffice.exceptions.CountryException;
import za.co.wirecard.channel.backoffice.models.City;
import za.co.wirecard.channel.backoffice.models.Country;
import za.co.wirecard.channel.backoffice.models.Province;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Value("${api.addressmanagement.country-url}")
    private String countryUrl;

    @Value("${api.addressmanagement.province-url}")
    private String provinceUrl;

    @Value("${api.addressmanagement.city-url}")
    private String cityUrl;

    private final RestTemplate restTemplate;
    private final UtilityService utilityService;

    private static final Logger logger = LogManager.getLogger(CountryService.class);

    public CountryServiceImpl(RestTemplate restTemplate, UtilityService utilityService) {
        this.restTemplate = restTemplate;
        this.utilityService = utilityService;
    }

    @Override
    public List<Country> getCountries() throws CountryException {
        ParameterizedTypeReference<List<Country>> responseType = new ParameterizedTypeReference<List<Country>>() {
        };
        ResponseEntity<List<Country>> countries;
        try {
            countries = restTemplate.exchange(countryUrl, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
        } catch (RestClientResponseException e) {
            throw new CountryException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the countries Object that gets returned: " + countries.getBody());
        return countries.getBody();
    }

    @Override
    public List<Province> getProvinces(long countryId) throws CountryException {
        ParameterizedTypeReference<List<Province>> responseType = new ParameterizedTypeReference<List<Province>>() {
        };
        ResponseEntity<List<Province>> provinces;
        try {
            provinces = restTemplate.exchange(String.format(provinceUrl, countryId), HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
        } catch (RestClientResponseException e) {
            throw new CountryException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the provinces Object that gets returned: " + provinces.getBody());
        return provinces.getBody();
    }

    @Override
    public List<City> getCities(long provinceId) throws CountryException {
        ParameterizedTypeReference<List<City>> responseType = new ParameterizedTypeReference<List<City>>() {
        };
        ResponseEntity<List<City>> cities;
        try {
            cities = restTemplate.exchange(String.format(cityUrl, provinceId), HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
        } catch (RestClientResponseException e) {
            throw new CountryException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the cities Object that gets returned: " + cities.getBody());
        return cities.getBody();
    }
}
