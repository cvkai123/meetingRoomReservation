package com.meetingroomreservation.service;


import java.util.List;

import com.meetingroomreservation.dto.LocationDto;

public interface LocationService {
    List<LocationDto> findAllOfficeLocation();
    
    public LocationDto getOfficeLocationById(Long theId);

    public void deleteOfficeLocation(Long theId);
    
    public LocationDto getOfficeLocation(String officeLocation);

	void saveOfficeLocation(LocationDto locationDto);
}
