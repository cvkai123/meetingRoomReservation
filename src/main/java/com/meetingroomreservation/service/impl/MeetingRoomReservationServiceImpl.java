package com.meetingroomreservation.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meetingroomreservation.dto.MeetingRoomReservationDto;
import com.meetingroomreservation.entity.MeetingRoomReservation;
import com.meetingroomreservation.repository.MeetingRoomReservationRepository;
import com.meetingroomreservation.service.MeetingRoomReservationService;

@Service
public class MeetingRoomReservationServiceImpl implements MeetingRoomReservationService {

    @Autowired
    private MeetingRoomReservationRepository meetingRoomReservationRepository;
    
    public MeetingRoomReservationServiceImpl(MeetingRoomReservationRepository meetingRoomReservationRepository) {
			this.meetingRoomReservationRepository = meetingRoomReservationRepository;
	}

    @Override
    @Transactional
    public List <MeetingRoomReservationDto> findAllMeetingRoomReservation() {
    	 List<MeetingRoomReservation> meetingRooms = meetingRoomReservationRepository.findAll();
         return meetingRooms.stream().map((meetingRoom) -> convertEntityToDto(meetingRoom))
                 .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveMeetingRoomReservation(MeetingRoomReservationDto meetingRoomDto) {
    	MeetingRoomReservation meetingRoom = new MeetingRoomReservation();
    	meetingRoom.setId(meetingRoomDto.getId());
    	meetingRoom.setUserId(meetingRoomDto.getUserId());
    	meetingRoom.setMeetingRoomId(meetingRoomDto.getMeetingRoomId());
    	meetingRoom.setMeetingDescription(meetingRoomDto.getMeetingDescription());
    	meetingRoom.setPrivateMeeting(meetingRoomDto.getPrivateMeeting());
    	meetingRoom.setStartTime(meetingRoomDto.getStartTime());
    	meetingRoom.setEndTime(meetingRoomDto.getEndTime());

    	meetingRoomReservationRepository.save(meetingRoom);
    }
    
    private MeetingRoomReservationDto convertEntityToDto(MeetingRoomReservation meetingRoom){
    	MeetingRoomReservationDto meetingRoomDto = new MeetingRoomReservationDto();
    	meetingRoomDto.setId(meetingRoomDto.getId());
    	meetingRoomDto.setUserId(meetingRoomDto.getUserId());
    	meetingRoomDto.setMeetingRoomId(meetingRoomDto.getMeetingRoomId());
    	meetingRoomDto.setMeetingDescription(meetingRoomDto.getMeetingDescription());
    	meetingRoomDto.setPrivateMeeting(meetingRoomDto.getPrivateMeeting());
    	meetingRoomDto.setStartTime(meetingRoomDto.getStartTime());
    	meetingRoomDto.setEndTime(meetingRoomDto.getEndTime());
        return meetingRoomDto;
    }

	@Override
	@Transactional
	public MeetingRoomReservationDto getMeetingRoomReservation(String officeLocation, String meetingRoom) {
		MeetingRoomReservationDto meetingRoomDto = new MeetingRoomReservationDto();
		MeetingRoomReservation meetingRoomDetail = meetingRoomReservationRepository.findByOfficeLocationIdAndMeetingRoomId(officeLocation,meetingRoom);
		if(meetingRoomDetail!=null) {
			meetingRoomDto = convertEntityToDto(meetingRoomDetail);
		}
		return meetingRoomDto;
	}

	@Override
	public MeetingRoomReservationDto getMeetingRoomReservationById(Long theId) {
		Optional<MeetingRoomReservation> meetingRoomDetail = meetingRoomReservationRepository.findById(theId);
		if(!meetingRoomDetail.isEmpty()) {
			return convertEntityToDto(meetingRoomDetail.get());
		}
		return null;
	}

	@Override
	public void deleteMeetingRoomReservation(Long theId) {
		meetingRoomReservationRepository.deleteById(theId);
	}


}
