package net.seren.cities.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.seren.cities.exception.CityAlreadyDefinedException;
import net.seren.cities.exception.CityNotFoundException;
import net.seren.cities.model.City;
import net.seren.cities.repository.CityRepository;
import net.seren.cities.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	private CityRepository cityRepository;

	
	public CityServiceImpl (CityRepository cityRepository){
		this.cityRepository = cityRepository;
	}

	@Override
	public Optional<City> getCityById(long id) {
		return cityRepository.findById(id);	
	}

	@Override
	public Page<City> findAll(Pageable paging) {
		return cityRepository.findAll(paging);
	}

	@Override
	public Page<City> findByName(String name, Pageable paging) {
		return cityRepository.findByNameContainingIgnoreCase(name, paging);
	}

	@Override
	public City updateCity(long id, City updatedCity) {
		Optional<City> cityData = cityRepository.findById(id);
		
		if (cityData.isEmpty()) {
			throw new CityNotFoundException();
		}
		
		Optional<City> cityDataByName = findByName(updatedCity.getName());
		
		if (cityDataByName.isPresent() && cityDataByName.get().getId() != id) {
			throw new CityAlreadyDefinedException(updatedCity.getName());
		}

		City city = cityData.get();
		city.setName(updatedCity.getName());
		city.setVersion(updatedCity.getVersion());
		city.setExtension(updatedCity.getExtension());
		return cityRepository.save(city);
	}

	@Override
	public Optional<City> findByName(String cityName) {
		return cityRepository.findByName(cityName);
	}

	@Override
	public City createCity(City city) {
		return cityRepository.save(city);
	}

}
