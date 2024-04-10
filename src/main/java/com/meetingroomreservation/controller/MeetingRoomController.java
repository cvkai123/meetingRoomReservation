package com.meetingroomreservation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.meetingroomreservation.dto.LocationDto;
import com.meetingroomreservation.dto.MeetingRoomDto;
import com.meetingroomreservation.service.LocationService;
import com.meetingroomreservation.service.MeetingRoomService;
import java.util.Map;
import java.util.HashMap;


import jakarta.validation.Valid;

@Controller
public class MeetingRoomController {

    private MeetingRoomService meetingRoomService;
    private LocationService locationService;

    public MeetingRoomController(MeetingRoomService meetingRoomService,LocationService locationService) {
        this.meetingRoomService = meetingRoomService;
        this.locationService = locationService;
    }
        
    @GetMapping("/meetingRoomManagement")
    public String meetingRoomManagement(Model model){
    	List<MeetingRoomDto> meetingRooms = meetingRoomService.findAllMeetingRoom();
    	for(MeetingRoomDto eachMeetingRoom : meetingRooms) {
    		eachMeetingRoom.setOfficeLocationId(getOfficeLocationFromId(eachMeetingRoom.getOfficeLocationId()));
    	}
    	
        model.addAttribute("meetingRooms", meetingRooms);
        return "meetingRoom";
    }
    
    @GetMapping("/showMeetingRoomForm")
    public String showMeetingRoomFormForAdd(Model theModel) {
    	MeetingRoomDto theMeetingRoom = new MeetingRoomDto();
    	List<LocationDto> locations = locationService.findAllOfficeLocation();
    	
    	if(!locations.isEmpty()) {
    		List<Map<String, Object>> locationListing = new ArrayList<>();
    		
    		for(LocationDto location : locations) {
    			Map<String, Object> locationMap = new HashMap<>();
    			locationMap.put("id", location.getId());
                locationMap.put("officeLocation", location.getOfficeLocation());
                locationListing.add(locationMap);
    		}
        	theModel.addAttribute("listing",locationListing);
    	}
    	    	
        theModel.addAttribute("meetingRoom", theMeetingRoom);
        return "meetingRoom-form";
    }
    
 // handler method to handle add meeting room form submit request
    @PostMapping("/addMeetingRoom/save")
    public String addMeetingRoom(@Valid @ModelAttribute("meetingRoom") MeetingRoomDto meetingRoomDto,
                               BindingResult result,
                               Model model){
        MeetingRoomDto existing = meetingRoomService.getMeetingRoom(meetingRoomDto.getOfficeLocationId(),meetingRoomDto.getMeetingRoom());
        if (existing.getOfficeLocationId()!=null) {
            result.rejectValue("meetingRoom", null, "There is already an meeting room added");
        }
        if (result.hasErrors()) {
            model.addAttribute("meetingRoom", meetingRoomDto);
            List<LocationDto> locations = locationService.findAllOfficeLocation();
        	
        	if(!locations.isEmpty()) {
        		List<String> locationListing = new ArrayList<>();
        		for(LocationDto location : locations) {
        			locationListing.add(location.getOfficeLocation());
        		}
        		model.addAttribute("listing",locationListing);
        	}
        	
            return "meetingRoom-form";
        }
        meetingRoomService.saveMeetingRoom(meetingRoomDto);
        return "redirect:/meetingRoomManagement";
    }
    
    @GetMapping("/editMeetingRoomForm/{id}") 
    public String showEditMeetingRoomFormForUpdate(@PathVariable ( value = "id") Long id, Model model) {
    	MeetingRoomDto theMeetingRoom = meetingRoomService.getMeetingRoomById(id);
    	theMeetingRoom.setOfficeLocationId(getOfficeLocationFromId(theMeetingRoom.getOfficeLocationId()));
    	model.addAttribute("meetingRoom", theMeetingRoom); 
    	return "meetingRoom-editform"; 
    }
    
    @GetMapping("/deleteMeetingRoomForm/{id}") 
    public String deleteMeetingRoom(@PathVariable ( value = "id") Long id, Model model) {
    	meetingRoomService.deleteMeetingRoom(id);
    	return "redirect:/meetingRoomManagement"; 
    }
    
    @PostMapping("/updateMeetingRoom/save")
    public String updateMeetingRoom(@Valid @ModelAttribute("meetingRoom") MeetingRoomDto meetingRoomDto,
                               BindingResult result,
                               Model model){
    	LocationDto locationDto = locationService.getOfficeLocation(meetingRoomDto.getOfficeLocationId());
    	if(locationDto!=null) {
    		meetingRoomDto.setOfficeLocationId(String.valueOf(locationDto.getId()));
    	}
    	
        meetingRoomService.saveMeetingRoom(meetingRoomDto);
        return "redirect:/meetingRoomManagement";
    }
    
    public String getOfficeLocationFromId(String id) {
    	LocationDto locationDto = locationService.getOfficeLocationById(Long.valueOf(id));
    	if(locationDto!=null) {
			return locationDto.getOfficeLocation();
		}
    	return null;
    }
}
