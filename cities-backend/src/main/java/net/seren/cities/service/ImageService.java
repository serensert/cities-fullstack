package net.seren.cities.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	void save(MultipartFile file, long id, String extension);

	void save(String url, long id);

	void uploadImage(MultipartFile imageFile, long id);

	byte[] getImageAsByteArray(String filename);
}
