package com.meetingroomreservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetingroomreservation.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
	
	Location findByOfficeLocation(String officeLocation);
        
    Optional<Location> findById(Long theId);
}
