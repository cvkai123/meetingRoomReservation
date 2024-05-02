package com.meetingroomreservation.dto;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoomReservationDto
{
    private Long id;
    
    @NotEmpty(message = "Office Location should not be empty")
    private String officeLocationId;
    
    @NotEmpty(message = "Meeting room should not be empty")
    private String meetingRoomId;
    
    @NotEmpty(message = "User should not be empty")
    private String userId;
    
    @NotEmpty(message = "Meeting Description should not be empty")
    private String meetingDescription;
    
    private String privateMeeting;
    
    @NotEmpty(message = "Start time should not be empty")
    private Date startTime;
    
    @NotEmpty(message = "End Time should not be empty")
    private Date endTime;
           
}
