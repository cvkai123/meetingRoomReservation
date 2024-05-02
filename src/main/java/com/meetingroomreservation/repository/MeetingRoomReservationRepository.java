package com.meetingroomreservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetingroomreservation.entity.MeetingRoomReservation;

public interface MeetingRoomReservationRepository extends JpaRepository<MeetingRoomReservation, Long> {
	
    //MeetingRoomReservation findByMeetingRoomReservation(String meetingRoom);
    
    MeetingRoomReservation findByOfficeLocationIdAndMeetingRoomId(String locationOfficeId, String meetingRoom);
    
    Optional<MeetingRoomReservation> findById(Long theId);
}
