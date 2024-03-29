package com.meetingroomreservation.dao;

import java.util.List;

import com.meetingroomreservation.entity.MeetingRoom;

public interface MeetingRoomDAO {

    public List<MeetingRoom> getMeetingRooms();

    public void saveMeetingRoom(MeetingRoom theMeetingRoom);

    public MeetingRoom getMeetingRoom(int theId);

    public void deleteMeetingRoom(int theId);
}
