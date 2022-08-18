package net.seren.cities.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.seren.cities.exception.CityImageNotSavedException;
import net.seren.cities.exception.CityNotFoundException;
import net.seren.cities.model.City;
import net.seren.cities.service.CityService;
import net.seren.cities.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Value("${images.path}")
	private String imagesPath;
	
	final static Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

	private String imagesPathOSDependent;

	private CityService cityService;

	public ImageServiceImpl(CityService cityService) {
		this.cityService = cityService;
	}

	@PostConstruct
	private void postContruct() {
		imagesPathOSDependent = imagesPath.replace("[PATH_SEPARATOR]", File.separator);
	}

	@Override
	public void save(MultipartFile file, long id, String extension) {
		try {
			Path root = Paths.get(imagesPathOSDependent);
			Path resolve = root.resolve(id + "." + extension);
			Files.copy(file.getInputStream(), resolve, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new CityImageNotSavedException();
		}
	}

	@Override
	public void save(String url, long id) {
		try {
			Path root = Paths.get(imagesPathOSDependent);
			String extension = url.substring(url.lastIndexOf(".") + 1);
			Path resolve = root.resolve(id + "." + extension);
			File file = new File(resolve.toString());
			if (!file.exists()) {
				FileUtils.copyURLToFile(new URL(url), new File(resolve.toString()), 1000000, 1000000);
			}
		} catch (Exception e) {
			logger.error("Could not store the file: " + url, e);
		}
	}

	@Override
	public void uploadImage(MultipartFile imageFile, long id) {
		Optional<City> city = cityService.getCityById(id);

		if (city.isEmpty()) {
			throw new CityNotFoundException();
		}

		String fileName = imageFile.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		save(imageFile, id, extension);

		city.get().setVersion(city.get().getVersion() + 1);
		city.get().setExtension(extension);
		cityService.updateCity(id, city.get());
	}

	@Override
	public byte[] getImageAsByteArray(String filename) {
		try {
			return FileUtils.readFileToByteArray(new File(imagesPathOSDependent + filename));
		} catch (IOException e) {
			logger.error("Image could not be read: " + filename);
		}
		return null;
	}
}