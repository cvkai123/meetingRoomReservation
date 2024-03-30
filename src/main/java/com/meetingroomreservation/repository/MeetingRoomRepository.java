package com.meetingroomreservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetingroomreservation.entity.MeetingRoom;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
	
    MeetingRoom findByMeetingRoom(String meetingRoom);
    
    MeetingRoom findByOfficeLocationAndMeetingRoom(String locationOffice, String meetingRoom);
    
    Optional<MeetingRoom> findById(Long theId);
}
