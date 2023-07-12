package com.digital.api.digital_booking.services;

import com.digital.api.digital_booking.models.City;
import com.digital.api.digital_booking.repositories.ICityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityService {

    private final ICityRepository cityRepository;

    public City createCity(City city) {


        return cityRepository.save(city);
    }

    public City updateCityById(Long idCity, City city) {
        City cityToUpdate = cityRepository.findByIdCity(idCity);

        if (cityToUpdate != null) {
            if (city.getNameCity() != null) {
                cityToUpdate.setNameCity(city.getNameCity());
            }
            if (city.getNameCountry() != null) {
                cityToUpdate.setNameCountry(city.getNameCountry());
            }
            return cityRepository.save(cityToUpdate);
        } else {
            return null;
        }
    }
    public City getCityById(Long idCity) {
        return cityRepository.findByIdCity(idCity);
    }
    public String deleteCity(Long idCity) {

       try {
            cityRepository.deleteById(idCity);
            return "City deleted";
        } catch (Exception e) {
            return "Ciudad no eliminada vulnera la integridad de la base de datos";
        }
    }

    public Iterable<City> getAll() {
        return cityRepository.findAll();
    }
}
