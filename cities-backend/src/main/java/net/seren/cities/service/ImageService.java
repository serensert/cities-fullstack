package net.seren.cities.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	public boolean save(MultipartFile file, long id, String extension);
	
	public boolean save(String url, long id);

	public boolean uploadImage(MultipartFile imageFile, long id);

	public byte[] getImageAsByteArray(String filename);
}
