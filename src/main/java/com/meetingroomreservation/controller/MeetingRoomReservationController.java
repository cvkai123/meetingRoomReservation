package com.meetingroomreservation.controller;

import java.io.Console;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
import java.util.Optional;
import java.util.HashMap;


import jakarta.validation.Valid;

@Controller
public class MeetingRoomReservationController {

	private MeetingRoomReservationService meetingRoomReservationService;
    private MeetingRoomService meetingRoomService;
    private LocationService locationService;
    private UserService userService;
    private String defaultLocationId;
    private String defaultRoomId;

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
    	List<MeetingRoomReservationDto> meetingRoomsReservation = meetingRoomReservationService.findAllMeetingRoomReservation();
    	for(MeetingRoomReservationDto eachMeetingRoomReservation : meetingRoomsReservation) {
    		eachMeetingRoomReservation.setOfficeLocationId(getOfficeLocationFromId(eachMeetingRoomReservation.getOfficeLocationId()));
    		eachMeetingRoomReservation.setMeetingRoomId(getMeetingRoomFromId(eachMeetingRoomReservation.getMeetingRoomId()));
    	}
    	List<Map<String, Object>> locationListing = getAllLocationList();
        if(!locationListing.isEmpty()) {
        	model.addAttribute("listing",locationListing);
        }
        
    	if(StringUtils.hasText(defaultLocationId)) {
    		model.addAttribute("defaultLocationId", Long.parseLong(defaultLocationId));
    		model.addAttribute("defaultRoomId", Long.parseLong(defaultRoomId));
    		defaultRoomId = null;
    		defaultLocationId = null;
    	}
        // Add the logged-in user ID to the model
        model.addAttribute("loggedInUserId", getUsernameId());
        
        model.addAttribute("meetingRoomsReservation", meetingRoomsReservation);
        return "meetingRoomReservationList";
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
    	
    	// Add the logged-in user ID to the model
    	theModel.addAttribute("loggedInUserId", getUsernameId());
        
        return "meetingRoomReservationList";
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
            result.rejectValue("meetingRoomName", null, "Meeting room has been reserved");
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
        
        MeetingRoomReservationDto theMeetingRoom = new MeetingRoomReservationDto();
        
        List<Map<String, Object>> locationListing = getAllLocationList();
        if(!locationListing.isEmpty()) {
        	model.addAttribute("listing",locationListing);
        }
    	
    	model.addAttribute("defaultLocationId", Long.parseLong(meetingRoomReservationDto.getOfficeLocationId()));
    	model.addAttribute("defaultRoomId", Long.parseLong(meetingRoomReservationDto.getMeetingRoomId()));
    	model.addAttribute("meetingRoomReservation", theMeetingRoom);
    	// Add the logged-in user ID to the model
        model.addAttribute("loggedInUserId", getUsernameId());
        
        return "meetingRoomReservationList";
    }
    
    @GetMapping("/editMeetingRoomReservationForm/{id}") 
    public String showEditMeetingRoomReservationFormForUpdate(@PathVariable ( value = "id") Long id, Model model) {
    	MeetingRoomReservationDto theMeetingRoomReservation = meetingRoomReservationService.getMeetingRoomReservationById(id);
    	theMeetingRoomReservation.setLocationName(getOfficeLocationFromId(theMeetingRoomReservation.getOfficeLocationId()));
    	
    	MeetingRoomDto meetingRoomDto = meetingRoomService.getMeetingRoomById(Long.valueOf(theMeetingRoomReservation.getMeetingRoomId()));
    	if(meetingRoomDto!=null) {
    		theMeetingRoomReservation.setAmenitiesInformation(meetingRoomDto.getAmenitiesInformation());
    		theMeetingRoomReservation.setMeetingRoomDescription(meetingRoomDto.getMeetingRoomDescription());
    		theMeetingRoomReservation.setMeetingRoomName(meetingRoomDto.getMeetingRoom());
		}
    	
    	// Format the start and end times
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedStartTime = theMeetingRoomReservation.getStartTime().format(formatter);
        String formattedEndTime = theMeetingRoomReservation.getEndTime().format(formatter);

        model.addAttribute("formattedStartTime", formattedStartTime);
        model.addAttribute("formattedEndTime", formattedEndTime);
    	
    	model.addAttribute("meetingRoomReservation", theMeetingRoomReservation); 
    	defaultLocationId = theMeetingRoomReservation.getOfficeLocationId();
    	defaultRoomId = theMeetingRoomReservation.getMeetingRoomId();
    	return "meetingRoomReservation-editform"; 
    }
    
    @GetMapping("/deleteMeetingRoomReservationForm/{id}") 
    public String deleteMeetingRoomReservationForm(@PathVariable ( value = "id") Long id, Model model) {
    	MeetingRoomReservationDto theMeetingRoomReservation = meetingRoomReservationService.getMeetingRoomReservationById(id);
    	
    	meetingRoomReservationService.deleteMeetingRoomReservation(id);
    	
    	defaultLocationId = theMeetingRoomReservation.getOfficeLocationId();
    	defaultRoomId = theMeetingRoomReservation.getMeetingRoomId();
    	
    	return "redirect:/meetingRoomReservationManagement"; 
    }
    
    @PostMapping("/updateMeetingRoomReservation/save")
    public String updateMeetingRoomReservation(@Valid @ModelAttribute("meetingRoomReservation") MeetingRoomReservationDto meetingRoomReservation,
                                               BindingResult result,
                                               Model model) {
        // Check for existing reservations that overlap with the requested time slot
        List<MeetingRoomReservationDto> existingReservations = meetingRoomReservationService.getMeetingRoomReservations(
                meetingRoomReservation.getOfficeLocationId(),
                meetingRoomReservation.getMeetingRoomId(),
                meetingRoomReservation.getStartTime(),
                meetingRoomReservation.getEndTime());
        
        if (!existingReservations.isEmpty()) {
            result.rejectValue("meetingRoomName", null, "Meeting room has already been reserved for the selected time slot.");
        }
        
        // Handle form validation errors
        if (result.hasErrors()) {
            model.addAttribute("meetingRoomReservation", meetingRoomReservation);            
            return "meetingRoomReservation-editform";
        }
        
        // Save the reservation if there are no errors
        meetingRoomReservationService.saveMeetingRoomReservation(meetingRoomReservation);
        return "redirect:/meetingRoomReservationManagement";
    }

    
    public String getOfficeLocationFromId(String id) {
    	LocationDto locationDto = locationService.getOfficeLocationById(Long.valueOf(id));
    	if(locationDto!=null) {
			return locationDto.getOfficeLocation();
		}
    	return null;
    }
    
    public List<Map<String, Object>> getAllLocationList(){
		List<LocationDto> locations = locationService.findAllOfficeLocation();
		
		if(!locations.isEmpty()) {
			List<Map<String, Object>> locationListing = new ArrayList<>();
			
			for(LocationDto location : locations) {
				Map<String, Object> locationMap = new HashMap<>();
				locationMap.put("id", location.getId());
	            locationMap.put("officeLocation", location.getOfficeLocation());
	            locationListing.add(locationMap);
			}
			return locationListing;
		}
		return null;
    }
    
    public String getMeetingRoomFromId(String id) {
    	MeetingRoomDto meetingRoomDto = meetingRoomService.getMeetingRoomById(Long.valueOf(id));
    	if(meetingRoomDto!=null) {
			return meetingRoomDto.getMeetingRoom();
		}
    	return null;
    }
    
    @GetMapping("/meetingRoomReservationForm")
    public String meetingRoomReservationForm(@RequestParam("meetingRoomId") String meetingRoomId, Model model) {
        
    	System.out.println(meetingRoomId);
    	MeetingRoomReservationDto theMeetingRoom = new MeetingRoomReservationDto();
    	MeetingRoomDto meetingRoomDto = meetingRoomService.getMeetingRoomById(Long.parseLong(meetingRoomId));
    	theMeetingRoom.setMeetingRoomId(meetingRoomDto.getId().toString());
    	theMeetingRoom.setOfficeLocationId(meetingRoomDto.getOfficeLocationId());
    	theMeetingRoom.setAmenitiesInformation(meetingRoomDto.getAmenitiesInformation());
    	theMeetingRoom.setMeetingRoomDescription(meetingRoomDto.getMeetingRoomDescription());
    	theMeetingRoom.setMeetingRoomName(meetingRoomDto.getMeetingRoom());
    	LocationDto location = locationService.getOfficeLocationById(Long.parseLong(meetingRoomDto.getOfficeLocationId()));
    	theMeetingRoom.setLocationName(location.getOfficeLocation());
    	    	    	
    	model.addAttribute("meetingRoomReservation", theMeetingRoom);
        // For demonstration purposes, let's just return a view name
        return "meetingRoomReservation-form"; // assuming you have a bookingPage.jsp or bookingPage.html
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
    
    @GetMapping("/getMeetingRoomReservationInfo")
    @ResponseBody
    public List<MeetingRoomReservationDto> getMeetingRoomReservationInfo(@RequestParam("id") Long id){
    	List<MeetingRoomReservationDto> arrayListMeetingRoomReservation = new ArrayList<>();
    	List<MeetingRoomReservationDto> meetingRoomReservations = meetingRoomReservationService.getMeetingRoomReservationsByMeetingRoom(id.toString());
    	String loginId = getUsernameId();
    	for(MeetingRoomReservationDto eachMeetingRoomReservation : meetingRoomReservations) {
    		String eachUserId = eachMeetingRoomReservation.getUserId().toString();
    		eachMeetingRoomReservation.setOfficeLocationId(getOfficeLocationFromId(eachMeetingRoomReservation.getOfficeLocationId()));
    		
    		Optional<User> u = userService.findById(Long.parseLong(eachMeetingRoomReservation.getUserId()));
    		if(!u.isEmpty()) {
    			eachMeetingRoomReservation.setUserId(u.get().getName());
    		}
    		
    		if(eachMeetingRoomReservation.getPrivateMeeting().equals("Yes")) {
    			eachMeetingRoomReservation.setMeetingDescription("***********");
    		}
    		
    		// Parse the string to LocalDateTime
            LocalDateTime startDateTime = LocalDateTime.parse(eachMeetingRoomReservation.getStartTime().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime endDateTime = LocalDateTime.parse(eachMeetingRoomReservation.getEndTime().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            // Format the LocalDateTime without the 'T'
            eachMeetingRoomReservation.setStrStartTime(startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            eachMeetingRoomReservation.setStrEndTime(endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            eachMeetingRoomReservation.setLoginId(loginId);
            eachMeetingRoomReservation.setBookingId(eachUserId);
    		arrayListMeetingRoomReservation.add(eachMeetingRoomReservation);
    	}
    	
        return arrayListMeetingRoomReservation;
    }    
    
    public String getUsernameId() {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User u = userService.findByEmail(auth.getName());
		if(u!=null) {
			return u.getId().toString();
		}
    	
        return null; 
    }
}
