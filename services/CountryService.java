package za.co.wirecard.channel.backoffice.services;

import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.exceptions.CountryException;
import za.co.wirecard.channel.backoffice.models.City;
import za.co.wirecard.channel.backoffice.models.Country;
import za.co.wirecard.channel.backoffice.models.Province;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface CountryService {

    List<Country> getCountries() throws CountryException;

    List<Province> getProvinces(long countryId) throws CountryException;

    List<City> getCities(long provinceId) throws CountryException;
}
