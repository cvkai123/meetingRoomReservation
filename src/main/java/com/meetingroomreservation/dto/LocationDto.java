package com.meetingroomreservation.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto
{
    private Long id;
    
    @NotEmpty(message = "Office location should not be empty")
    private String officeLocation;
    
           
}
