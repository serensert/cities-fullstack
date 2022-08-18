package net.seren.cities.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.seren.cities.model.City;

public interface CityService {

	Optional<City> getCityById(long id);

	Page<City> findAll(Pageable paging);

	Page<City> findByName(String name, Pageable paging);

	City updateCity(long id, City city);

	Optional<City> findByName(String cityName);

	City createCity(City c);

}
