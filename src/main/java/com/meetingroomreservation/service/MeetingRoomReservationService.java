package com.meetingroomreservation.service;


import java.time.LocalDateTime;
import java.util.List;

import com.meetingroomreservation.dto.MeetingRoomReservationDto;

public interface MeetingRoomReservationService {
    List<MeetingRoomReservationDto> findAllMeetingRoomReservation();
    
    public MeetingRoomReservationDto getMeetingRoomReservationById(Long theId);

    public void deleteMeetingRoomReservation(Long theId);
    
    public List<MeetingRoomReservationDto> getMeetingRoomReservations(String officeLocation, String meetingRoom, LocalDateTime startDate, LocalDateTime endDate);

	void saveMeetingRoomReservation(MeetingRoomReservationDto meetingRoomReservationDto);

	List<MeetingRoomReservationDto> getMeetingRoomReservationsByMeetingRoom(String meetingRoom);
}
