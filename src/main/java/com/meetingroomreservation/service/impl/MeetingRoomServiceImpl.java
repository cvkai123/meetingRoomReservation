package com.meetingroomreservation.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    	meetingRoom.setId(meetingRoomDto.getId());
    	meetingRoom.setMeetingRoom(meetingRoomDto.getMeetingRoom());
    	meetingRoom.setMeetingRoomDescription(meetingRoomDto.getMeetingRoomDescription());
    	meetingRoom.setAmenitiesInformation(meetingRoomDto.getAmenitiesInformation());
    	meetingRoom.setOfficeLocationId(meetingRoomDto.getOfficeLocationId());

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
    	meetingRoomDto.setId(meetingRoom.getId());
    	meetingRoomDto.setMeetingRoom(meetingRoom.getMeetingRoom());
    	meetingRoomDto.setMeetingRoomDescription(meetingRoom.getMeetingRoomDescription());
    	meetingRoomDto.setOfficeLocationId(meetingRoom.getOfficeLocationId());
    	meetingRoomDto.setAmenitiesInformation(meetingRoom.getAmenitiesInformation());
        return meetingRoomDto;
    }
    
    private MeetingRoom convertEntity(MeetingRoom meetingRoom){
    	MeetingRoom meetingRoomEntity = new MeetingRoom();
    	meetingRoomEntity.setId(meetingRoom.getId());
    	meetingRoomEntity.setMeetingRoom(meetingRoom.getMeetingRoom());
    	meetingRoomEntity.setMeetingRoomDescription(meetingRoom.getMeetingRoomDescription());
    	meetingRoomEntity.setOfficeLocationId(meetingRoom.getOfficeLocationId());
    	meetingRoomEntity.setAmenitiesInformation(meetingRoom.getAmenitiesInformation());
        return meetingRoomEntity;
    }

	@Override
	@Transactional
	public MeetingRoomDto getMeetingRoom(String officeLocation, String meetingRoom) {
		MeetingRoomDto meetingRoomDto = new MeetingRoomDto();
		MeetingRoom meetingRoomDetail = meetingRoomRepository.findByOfficeLocationIdAndMeetingRoom(officeLocation,meetingRoom);
		if(meetingRoomDetail!=null) {
			meetingRoomDto = convertEntityToDto(meetingRoomDetail);
		}
		return meetingRoomDto;
	}

	@Override
	public MeetingRoomDto getMeetingRoomById(Long theId) {
		Optional<MeetingRoom> meetingRoomDetail = meetingRoomRepository.findById(theId);
		if(!meetingRoomDetail.isEmpty()) {
			return convertEntityToDto(meetingRoomDetail.get());
		}
		return null;
	}
		
	@Override
    @Transactional
    public List <MeetingRoomDto> getMeetingRoomByOfficeLocationId(String theId) {
    	 List<MeetingRoom> meetingRooms = meetingRoomRepository.getMeetingRoomByOfficeLocationId(theId);
    	 return meetingRooms.stream().map((meetingRoom) -> convertEntityToDto(meetingRoom))
                 .collect(Collectors.toList());
    }

	@Override
	public void deleteMeetingRoom(Long theId) {
		meetingRoomRepository.deleteById(theId);
	}


}
