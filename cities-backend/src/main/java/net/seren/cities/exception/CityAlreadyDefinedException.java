package net.seren.cities.exception;

public class CityAlreadyDefinedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cityName;
		
	public CityAlreadyDefinedException(String cityName) {
		super();
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}

}
