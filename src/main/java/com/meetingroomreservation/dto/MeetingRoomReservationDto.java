package com.meetingroomreservation.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    
    private String userId;
    
    @NotEmpty(message = "Meeting Description should not be empty")
    private String meetingDescription;
    
    private String privateMeeting;
    
    @NotNull(message = "Start time should not be empty")
    private LocalDateTime startTime;
    
    @NotNull(message = "End Time should not be empty")
    private LocalDateTime endTime;
    
    private String strStartTime;
    
    private String strEndTime;
    
    private String locationName;
    private String meetingRoomName;
    private String amenitiesInformation;
    private String meetingRoomDescription;
           
}
