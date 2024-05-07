package com.meetingroomreservation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meetingroomreservation.dto.LocationDto;
import com.meetingroomreservation.dto.MeetingRoomDto;
import com.meetingroomreservation.dto.MeetingRoomReservationDto;
import com.meetingroomreservation.entity.User;
import com.meetingroomreservation.service.LocationService;
import com.meetingroomreservation.service.MeetingRoomReservationService;
import com.meetingroomreservation.service.MeetingRoomService;
import com.meetingroomreservation.service.UserService;

import java.util.Map;
import java.util.HashMap;


import jakarta.validation.Valid;

@Controller
public class MeetingRoomReservationController {

	private MeetingRoomReservationService meetingRoomReservationService;
    private MeetingRoomService meetingRoomService;
    private LocationService locationService;
    private UserService userService;

    public MeetingRoomReservationController(MeetingRoomService meetingRoomService,
    		LocationService locationService, 
    		MeetingRoomReservationService meetingRoomReservationService,
    		UserService userService) {
        this.meetingRoomService = meetingRoomService;
        this.locationService = locationService;
        this.meetingRoomReservationService = meetingRoomReservationService;
        this.userService = userService;
    }
        
    @GetMapping("/meetingRoomReservationManagement")
    public String meetingRoomReservationManagement(Model model){
    	List<MeetingRoomDto> meetingRooms = meetingRoomService.findAllMeetingRoom();
    	for(MeetingRoomDto eachMeetingRoom : meetingRooms) {
    		eachMeetingRoom.setOfficeLocationId(getOfficeLocationFromId(eachMeetingRoom.getOfficeLocationId()));
    	}
    	
        model.addAttribute("meetingRooms", meetingRooms);
        return "mainScreenBooking";
    }
    
    @GetMapping("/showMeetingRoomReservationForm")
    public String showMeetingRoomReservationFormForAdd(Model theModel) {
    	MeetingRoomReservationDto theMeetingRoom = new MeetingRoomReservationDto();
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
    @PostMapping("/addMeetingRoomReservation/save")
    public String addMeetingRoomReservation(@Valid @ModelAttribute("meetingRoomReservation") MeetingRoomReservationDto meetingRoomReservationDto,
                               BindingResult result,
                               Model model){
    	List<MeetingRoomReservationDto> existing = meetingRoomReservationService.getMeetingRoomReservations(
    			meetingRoomReservationDto.getOfficeLocationId(),meetingRoomReservationDto.getMeetingRoomId(),
    			meetingRoomReservationDto.getStartTime(),meetingRoomReservationDto.getEndTime());
        if (existing.size()>0) {
            result.rejectValue("meetingRoomReservation", null, "Meeting room has been reserve");
        }
        if (result.hasErrors()) {
            model.addAttribute("meetingRoomReservation", meetingRoomReservationDto);
			/*
			 * List<LocationDto> locations = locationService.findAllOfficeLocation();
			 * 
			 * if(!locations.isEmpty()) { List<String> locationListing = new ArrayList<>();
			 * for(LocationDto location : locations) {
			 * locationListing.add(location.getOfficeLocation()); }
			 * model.addAttribute("listing",locationListing); }
			 */
        	
            return "meetingRoomReservation-form";
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByEmail(username);
        meetingRoomReservationDto.setUserId(user.getId().toString());
        
        meetingRoomReservationService.saveMeetingRoomReservation(meetingRoomReservationDto);
        return "redirect:/meetingRoomReservation-form";
    }
    
    @GetMapping("/editMeetingRoomReservationForm/{id}") 
    public String showEditMeetingRoomReservationFormForUpdate(@PathVariable ( value = "id") Long id, Model model) {
    	MeetingRoomDto theMeetingRoom = meetingRoomService.getMeetingRoomById(id);
    	theMeetingRoom.setOfficeLocationId(getOfficeLocationFromId(theMeetingRoom.getOfficeLocationId()));
    	model.addAttribute("meetingRoom", theMeetingRoom); 
    	return "meetingRoom-editform"; 
    }
    
    @GetMapping("/deleteMeetingRoomReservationForm/{id}") 
    public String deleteMeetingRoomReservationForm(@PathVariable ( value = "id") Long id, Model model) {
    	meetingRoomService.deleteMeetingRoom(id);
    	return "redirect:/meetingRoomManagement"; 
    }
    
    @PostMapping("/updateMeetingRoomReservation/save")
    public String updateMeetingRoomReservation(@Valid @ModelAttribute("meetingRoom") MeetingRoomDto meetingRoomDto,
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
    
    @GetMapping("/meetingRoomReservationLocationRoom")
	public String meetingRoomReservationLocationRoom(Model model) {
    	MeetingRoomReservationDto theMeetingRoom = new MeetingRoomReservationDto();
    	List<LocationDto> locations = locationService.findAllOfficeLocation();
    	
    	if(!locations.isEmpty()) {
    		List<Map<String, Object>> locationListing = new ArrayList<>();
    		
    		for(LocationDto location : locations) {
    			Map<String, Object> locationMap = new HashMap<>();
    			locationMap.put("id", location.getId());
                locationMap.put("officeLocation", location.getOfficeLocation());
                locationListing.add(locationMap);
    		}
    		model.addAttribute("listing",locationListing);
    	}
    	    	    	
    	model.addAttribute("meetingRoomReservation", theMeetingRoom);
		return "meetingRoomReservation-form";
	}
    
    @GetMapping("/retrieveMeetingRooms")
    @ResponseBody
    public List<MeetingRoomDto> retrieveMeetingRooms(@RequestParam("locationId") String locationId) {
        // Call the service method to retrieve meeting rooms based on the locationId
        List<MeetingRoomDto> meetingRooms = meetingRoomService.getMeetingRoomByOfficeLocationId(locationId);
        return meetingRooms;
    }
    
    @GetMapping("/getMeetingRoomInfo")
    @ResponseBody
    public MeetingRoomDto getMeetingRoomInfo(@RequestParam("id") Long id) {
        // Call the service method to retrieve detailed information about the meeting room
        MeetingRoomDto meetingRoom = meetingRoomService.getMeetingRoomById(id);
        return meetingRoom;
    }
    
}
