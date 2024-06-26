package com.meetingroomreservation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetingroomreservation.entity.MeetingRoomReservation;

public interface MeetingRoomReservationRepository extends JpaRepository<MeetingRoomReservation, Long> {
	
    //MeetingRoomReservation findByMeetingRoomReservation(String meetingRoom);
    
	List<MeetingRoomReservation> findByOfficeLocationIdAndMeetingRoomId(String locationOfficeId, String meetingRoom);
	
	List<MeetingRoomReservation> findByMeetingRoomId(String meetingRoom);
    
    Optional<MeetingRoomReservation> findById(Long theId);
}
