package com.meetingroomreservation.service;


import java.util.List;

import com.meetingroomreservation.dto.MeetingRoomDto;

public interface MeetingRoomService {
    List<MeetingRoomDto> findAllMeetingRoom();
    
    //public MeetingRoomDto getMeetingRoom(int theId);

    //public void deleteMeetingRoom(int theId);

	void saveMeetingRoom(MeetingRoomDto meetingRoomDto);
}
