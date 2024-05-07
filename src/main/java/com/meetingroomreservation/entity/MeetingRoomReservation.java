package com.meetingroomreservation.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="meeting_room_reservation")
public class MeetingRoomReservation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="office_location_id", nullable=false)
    private String officeLocationId;
    
    @Column(name="meeting_room_id", nullable=false)
    private String meetingRoomId;
    
    @Column(name="user_id", nullable=false)
    private String userId;
    
    @Column(name="meeting_description", nullable=false)
    private String meetingDescription;
    
    @Column(name="private_meeting", nullable=true)
    private String privateMeeting;

    @Column(name="start_time", nullable=false)
    private LocalDateTime startTime;
    
    @Column(name="end_time", nullable=false)
    private LocalDateTime endTime;
    
}
