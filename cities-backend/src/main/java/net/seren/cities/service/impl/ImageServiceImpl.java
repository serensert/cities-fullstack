package net.seren.cities.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.seren.cities.model.City;
import net.seren.cities.service.CityService;
import net.seren.cities.service.ImageService;


@Service
public class ImageServiceImpl implements ImageService{

	@Value("${images.path}")
    private String imagesPath;
	
    private String imagesPathOSDependent;

	
	private CityService cityService;
	
	public ImageServiceImpl(CityService cityService) {
		this.cityService = cityService;
	}
	
	@PostConstruct
	private void postContruct( ) {
		imagesPathOSDependent = imagesPath.replace("[PATH_SEPARATOR]", File.separator);
	}

    public boolean save(MultipartFile file, long id, String extension) {
        try {
            Path root = Paths.get(imagesPathOSDependent);
            Path resolve = root.resolve(id + "." + extension);
            Files.copy(file.getInputStream(), resolve, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean save(String url, long id) {
        try {
            Path root = Paths.get(imagesPathOSDependent);
	    	String extension = url.substring(url.lastIndexOf(".") + 1);
            Path resolve = root.resolve(id + "." + extension);
            File file = new File(resolve.toString());
            if (!file.exists()) {
            	FileUtils.copyURLToFile(
          			  new URL(url), 
          			  new File(resolve.toString()), 
          			  1000000, 
          			  1000000);            	
            }
        } catch (Exception e) {
            System.out.println("Could not store the file: " + url);
        }
    	return true;
    }

	public boolean uploadImage(MultipartFile imageFile, long id) {
		City city = cityService.getCityById(id);
		
		if (city == null) {
			return false;
		}

    	String fileName = imageFile.getOriginalFilename();
    	String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
    	if (!save(imageFile, id, extension)) {
    		return false;
    	}
    	System.out.println(fileName);
    	System.out.println(extension);
    	
		city.setVersion(city.getVersion() + 1);
		city.setExtension(extension);
		cityService.updateCity(id, city);

		return true;
		
	}

	@Override
	public byte[] getImageAsByteArray(String filename) {
        try {
            return FileUtils.readFileToByteArray(new File(imagesPathOSDependent + filename));
        } catch (IOException e) {
            System.out.println("Image could not be found: " + filename);
        }
		return null;
	}
}