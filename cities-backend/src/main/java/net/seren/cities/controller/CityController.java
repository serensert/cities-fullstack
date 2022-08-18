package net.seren.cities.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.seren.cities.model.City;
import net.seren.cities.model.dto.CitiesPagedDto;
import net.seren.cities.model.dto.CityDto;
import net.seren.cities.service.CityService;
import net.seren.cities.service.impl.CityServiceImpl;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1/")
public class CityController {

	private CityService cityService;
	private ModelMapper modelMapper;

	public CityController(CityServiceImpl cityService, ModelMapper modelMapper) {
		this.cityService = cityService;
		this.modelMapper = modelMapper;
	}

	@GetMapping("/cities")
	public ResponseEntity<CitiesPagedDto> getAllCities(@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
		try {
			Pageable paging = PageRequest.of(page, size);

			Page<City> pagedCities;

			if (name == null)
				pagedCities = cityService.findAll(paging);
			else
				pagedCities = cityService.findByName(name, paging);

			if (pagedCities.getContent().isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(modelMapper.map(pagedCities, CitiesPagedDto.class), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/cities/{id}")
	public ResponseEntity<CityDto> getCityById(@PathVariable("id") long id) {
		Optional<City> city = cityService.getCityById(id);
		if (city.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<CityDto>(modelMapper.map(city.get(), CityDto.class), HttpStatus.OK);
		
	}

	@PutMapping("/cities/{id}")
	public ResponseEntity<CityDto> updateCity(@PathVariable("id") long id, @RequestBody CityDto cityDto) {
		City updatedCity = cityService.updateCity(id, modelMapper.map(cityDto, City.class));
		return new ResponseEntity<>(modelMapper.map(updatedCity, CityDto.class), HttpStatus.OK);
	}

}
