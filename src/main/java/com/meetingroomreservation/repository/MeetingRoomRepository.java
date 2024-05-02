package com.meetingroomreservation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetingroomreservation.entity.MeetingRoom;


public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
	
    MeetingRoom findByMeetingRoom(String meetingRoom);
    
    MeetingRoom findByOfficeLocationIdAndMeetingRoom(String locationOfficeId, String meetingRoom);
    
    Optional<MeetingRoom> findById(Long theId);
    
    List<MeetingRoom> getMeetingRoomByOfficeLocationId(String theId);
}
