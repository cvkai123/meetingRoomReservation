package com.meetingroomreservation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meetingroomreservation.dao.MeetingRoomDAO;
import com.meetingroomreservation.dto.MeetingRoomDto;
import com.meetingroomreservation.entity.MeetingRoom;
import com.meetingroomreservation.repository.MeetingRoomRepository;
import com.meetingroomreservation.service.MeetingRoomService;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    
    public MeetingRoomServiceImpl(MeetingRoomRepository meetingRoomRepository) {
			this.meetingRoomRepository = meetingRoomRepository;
	}

    @Override
    @Transactional
    public List <MeetingRoomDto> findAllMeetingRoom() {
    	 List<MeetingRoom> meetingRooms = meetingRoomRepository.findAll();
         return meetingRooms.stream().map((meetingRoom) -> convertEntityToDto(meetingRoom))
                 .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveMeetingRoom(MeetingRoomDto meetingRoomDto) {
    	MeetingRoom meetingRoom = new MeetingRoom();
    	meetingRoom.setMeetingRoom(meetingRoomDto.getAmenitiesInformation());
    	meetingRoom.setMeetingRoomDescription(meetingRoomDto.getMeetingRoomDescription());
    	meetingRoom.setAmenitiesInformation(meetingRoomDto.getAmenitiesInformation());
    	meetingRoom.setOfficeLocation(meetingRoomDto.getOfficeLocation());

    	meetingRoomRepository.save(meetingRoom);
    }

	/*
	 * @Override
	 * 
	 * @Transactional public MeetingRoom getMeetingRoom(int theId) { return
	 * meetingRoomDao.getMeetingRoom(theId); }
	 * 
	 * @Override
	 * 
	 * @Transactional public void deleteMeetingRoom(int theId) {
	 * meetingRoomDao.deleteMeetingRoom(theId); }
	 */
    
    private MeetingRoomDto convertEntityToDto(MeetingRoom meetingRoom){
    	MeetingRoomDto meetingRoomDto = new MeetingRoomDto();
    	meetingRoomDto.setId(meetingRoomDto.getId());
    	meetingRoomDto.setMeetingRoom(meetingRoom.getMeetingRoom());
    	meetingRoomDto.setMeetingRoomDescription(meetingRoom.getMeetingRoomDescription());
    	meetingRoomDto.setOfficeLocation(meetingRoom.getOfficeLocation());
    	meetingRoomDto.setAmenitiesInformation(meetingRoom.getAmenitiesInformation());
        return meetingRoomDto;
    }


}
