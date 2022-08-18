package net.seren.cities.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleException(CityNotFoundException cnf){
        return new ResponseEntity<>("City is not found!", HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler
    public ResponseEntity<Object> handleException(CityAlreadyDefinedException cad){
        return new ResponseEntity<>("City " + cad.getCityName() + " is already defined!", HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public ResponseEntity<Object> handleException(CityImageNotSavedException cins){
        return new ResponseEntity<>("City image could not be saved!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
