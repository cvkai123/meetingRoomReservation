package com.meetingroomreservation.entity;

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
@Table(name="meeting_room")
public class MeetingRoom
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="office_location", nullable=false, unique=true)
    private String officeLocation;
    
    @Column(name="meeting_room", nullable=false, unique=true)
    private String meetingRoom;

    @Column(name="meeting_room_description", nullable=true)
    private String meetingRoomDescription;
    
    @Column(name="amenities_information", nullable=true)
    private String amenitiesInformation;
    
}
