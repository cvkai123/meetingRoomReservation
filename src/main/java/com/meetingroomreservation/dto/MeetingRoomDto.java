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
public class MeetingRoomDto
{
    private Long id;
    
    @NotEmpty(message = "Office location should not be empty")
    private String officeLocationId;
    
    @NotEmpty(message = "Meeting room should not be empty")
    private String meetingRoom;
    
    @NotEmpty(message = "Meeting room description should not be empty")
    private String meetingRoomDescription;
    
    
    private String amenitiesInformation;
           
}
