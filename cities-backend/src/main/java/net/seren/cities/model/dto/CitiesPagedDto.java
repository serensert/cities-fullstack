package net.seren.cities.model.dto;

import java.util.List;

public class CitiesPagedDto {
	
	public List<CityDto> content;
	
	public long totalElements;

	public List<CityDto> getContent() {
		return content;
	}

	public void setContent(List<CityDto> content) {
		this.content = content;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

}
