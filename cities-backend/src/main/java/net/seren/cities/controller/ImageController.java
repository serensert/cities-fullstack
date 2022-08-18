package net.seren.cities.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.seren.cities.model.UploadResponseMessage;
import net.seren.cities.service.ImageService;
import net.seren.cities.service.impl.ImageServiceImpl;

@RestController
@RequestMapping("/images")
public class ImageController {

	private ImageService imageService;

	public ImageController(ImageServiceImpl imageService) {
		this.imageService = imageService;
	}

	@GetMapping("/{filename}")
	public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
		byte[] image = imageService.getImageAsByteArray(filename);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
				.cacheControl(CacheControl.maxAge(360, TimeUnit.DAYS)).body(image);
	}

	@PostMapping("/{id}")
	public ResponseEntity<UploadResponseMessage> uploadImage(@RequestParam("image") MultipartFile imageFile,
			@PathVariable("id") long id) {
		imageService.uploadImage(imageFile, id);
		return ResponseEntity.status(HttpStatus.OK).body(new UploadResponseMessage("Success"));				
	}

}
