package net.seren.cities.service.impl;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import net.seren.cities.model.City;
import net.seren.cities.service.CityService;
import net.seren.cities.util.CitiesUtil;

@Service
public class InitializerServiceImpl {
	
	ResourceLoader resourceLoader;
	
	ImageServiceImpl imageService;
		
	CityService cityService;
	
	public InitializerServiceImpl (ResourceLoader resourceLoader, ImageServiceImpl imageService, CityService cityService) {
		this.resourceLoader = resourceLoader;
		this.imageService = imageService;
		this.cityService = cityService;
	}

	@PostConstruct
	public void init() {
		System.out.println("Initialization started!");
		try {
			Resource resource = resourceLoader.getResource("classpath:" + "cities.csv");
			Reader in = new FileReader(resource.getFile());
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			boolean firstSkipped = false;
			int samecounter = 0;
			HashMap<String, String> cities = new HashMap<>();
			int counter = 0;
			for (CSVRecord record : records) {
				if (!firstSkipped) {
					firstSkipped = true;
					continue;
				}
			    String cityName = record.get(1);
			    String imageUrl = record.get(2);
			    if (cities.containsKey(cityName)) {
			    	System.out.println(cityName);
			    	System.out.println(cities.get(cityName));
			    	System.out.println(imageUrl);
			    	if (imageUrl.equals(cities.get(cityName))) {
			    		System.out.println("samecounter: " + ++samecounter);
			    	}
			    	continue; // skip duplicates;
			    } else {
			    	cities.put(cityName, imageUrl);
			    }
			    
			    long id = 0;
		    	City city = cityService.findByName(cityName);
		    	if (city == null) {
			    	String extension = CitiesUtil.getFileExtension(imageUrl);
		    		City c = new City();
		    		c.setName(cityName);
		    		c.setExtension(extension);
		    		c.setVersion(1);
		    		id = cityService.save(c).getId();
		    	} else {
		    		id = city.getId();
		    	}
			    System.out.println(id);

			    imageService.save(imageUrl, id);			    
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
