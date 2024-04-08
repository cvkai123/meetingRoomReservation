package com.meetingroomreservation.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meetingroomreservation.dto.LocationDto;
import com.meetingroomreservation.entity.Location;
import com.meetingroomreservation.repository.LocationRepository;
import com.meetingroomreservation.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;
    
    public LocationServiceImpl(LocationRepository locationRepository) {
			this.locationRepository = locationRepository;
	}

    @Override
    @Transactional
    public List <LocationDto> findAllOfficeLocation() {
    	 List<Location> locations = locationRepository.findAll();
         return locations.stream().map((location) -> convertEntityToDto(location))
                 .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveOfficeLocation(LocationDto locationDto) {
    	Location location = new Location();
    	location.setId(locationDto.getId());
    	location.setOfficeLocation(locationDto.getOfficeLocation());
    	locationRepository.save(location);
    }
    
    private LocationDto convertEntityToDto(Location location){
    	LocationDto locationDto = new LocationDto();
    	locationDto.setId(location.getId());
    	locationDto.setOfficeLocation(location.getOfficeLocation());
        return locationDto;
    }

	@Override
	@Transactional
	public LocationDto getOfficeLocation(String officeLocation) {
		LocationDto locationDto = new LocationDto();
		Location locationDetail = locationRepository.findByOfficeLocation(officeLocation);
		if(locationDetail!=null) {
			locationDto = convertEntityToDto(locationDetail);
		}
		return locationDto;
	}

	@Override
	public LocationDto getOfficeLocationById(Long theId) {
		Optional<Location> locationDetail = locationRepository.findById(theId);
		if(!locationDetail.isEmpty()) {
			return convertEntityToDto(locationDetail.get());
		}
		return null;
	}

	@Override
	public void deleteOfficeLocation(Long theId) {
		locationRepository.deleteById(theId);
	}


}
