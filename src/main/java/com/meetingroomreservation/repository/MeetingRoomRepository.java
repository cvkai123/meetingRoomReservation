package com.meetingroomreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetingroomreservation.entity.MeetingRoom;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
	
    MeetingRoom findByMeetingRoom(String meetingRoom);
}
