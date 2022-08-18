package net.seren.cities.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.seren.cities.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
	Page<City> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Optional<City> findByName(String name);
}
