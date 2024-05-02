package com.meetingroomreservation.service;


import java.util.List;

import com.meetingroomreservation.dto.MeetingRoomDto;

public interface MeetingRoomService {
    List<MeetingRoomDto> findAllMeetingRoom();
    
    public MeetingRoomDto getMeetingRoomById(Long theId);

    public void deleteMeetingRoom(Long theId);
    
    public MeetingRoomDto getMeetingRoom(String officeLocation, String meetingRoom);

	void saveMeetingRoom(MeetingRoomDto meetingRoomDto);
	
	public List<MeetingRoomDto> getMeetingRoomByOfficeLocationId(String theId);
}
