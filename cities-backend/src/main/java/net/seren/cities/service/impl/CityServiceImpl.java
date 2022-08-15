package net.seren.cities.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.seren.cities.model.City;
import net.seren.cities.repository.CityRepository;
import net.seren.cities.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	private CityRepository cityRepository;
	
	public CityServiceImpl (CityRepository cityRepository){
		this.cityRepository = cityRepository;
	}

	public City getCityById(long id) {
		Optional<City> city = cityRepository.findById(id);
		return city.isPresent()?city.get():null;		
	}

	public Page<City> findAll(Pageable paging) {
		return cityRepository.findAll(paging);
	}

	public Page<City> findByName(String name, Pageable paging) {
		// TODO Auto-generated method stub
		return cityRepository.findByNameContainingIgnoreCase(name, paging);
	}

	public City updateCity(long id, City city) {
		Optional<City> cityData = cityRepository.findById(id);

		if (cityData.isPresent()) {
			City _city = cityData.get();
			System.out.println(_city.getId());
			_city.setName(city.getName());
			_city.setVersion(city.getVersion());
			_city.setExtension(city.getExtension());
			return cityRepository.save(_city);
		} else {
			return null;
		}
	}

	@Override
	public City findByName(String cityName) {
		Optional<City> city = cityRepository.findByName(cityName);
		return city.isPresent() ? city.get() : null;
	}

	@Override
	public City save(City city) {
		return cityRepository.save(city);
	}

}
