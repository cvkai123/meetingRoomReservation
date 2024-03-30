package com.meetingroomreservation.service;


import java.util.List;

import com.meetingroomreservation.dto.MeetingRoomDto;

public interface MeetingRoomService {
    List<MeetingRoomDto> findAllMeetingRoom();
    
    //public MeetingRoomDto getMeetingRoom(int theId);

    //public void deleteMeetingRoom(int theId);
    
    public MeetingRoomDto getMeetingRoom(String officeLocation, String meetingRoom);

	void saveMeetingRoom(MeetingRoomDto meetingRoomDto);
}
