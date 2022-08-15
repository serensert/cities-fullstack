package net.seren.cities.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import net.seren.cities.service.CityService;
import net.seren.cities.service.impl.CityServiceImpl;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CityController {

	private CityService cityService;
	
	public CityController(CityServiceImpl cityService) {
		this.cityService = cityService;
	}

	@GetMapping("/cities")
	public ResponseEntity<Map<String, Object>> getAllCities(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "0") int page,
		    @RequestParam(defaultValue = "5") int size) {
		try {
			List<City> cities = new ArrayList<City>();
			Pageable paging = PageRequest.of(page, size);
			
			Page<City> pageCities;

			if (name == null)
				pageCities = cityService.findAll(paging);
			else
				pageCities = cityService.findByName(name, paging);
			
			cities = pageCities.getContent();

			if (cities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
		      Map<String, Object> response = new HashMap<>();
		      response.put("cities", cities);
		      response.put("currentPage", pageCities.getNumber());
		      response.put("totalItems", pageCities.getTotalElements());
		      response.put("totalPages", pageCities.getTotalPages());
		      return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/cities/{id}")
	public ResponseEntity<City> getCityById(@PathVariable("id") long id) {
		City city = cityService.getCityById(id);
		return city != null ? new ResponseEntity<>(city, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/cities/{id}")
	public ResponseEntity<City> updateCity(@PathVariable("id") long id, @RequestBody City city) {
		City updatedCity = cityService.updateCity(id, city);
		return updatedCity != null ? new ResponseEntity<>(updatedCity, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
