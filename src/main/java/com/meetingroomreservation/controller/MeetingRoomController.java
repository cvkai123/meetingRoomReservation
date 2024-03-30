package com.meetingroomreservation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.meetingroomreservation.dto.MeetingRoomDto;
import com.meetingroomreservation.service.MeetingRoomService;

import jakarta.validation.Valid;

@Controller
public class MeetingRoomController {

    private MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }
    
    @GetMapping("/meetingRoomManagement")
    public String meetingRoomManagement(Model model){
    	List<MeetingRoomDto> meetingRooms = meetingRoomService.findAllMeetingRoom();
        model.addAttribute("meetingRooms", meetingRooms);
        return "meetingRoom";
    }
    
    @GetMapping("/showMeetingRoomForm")
    public String showMeetingRoomFormForAdd(Model theModel) {
    	MeetingRoomDto theMeetingRoom = new MeetingRoomDto();
        theModel.addAttribute("meetingRoom", theMeetingRoom);
        return "meetingRoom-form";
    }
    
 // handler method to handle add meeting room form submit request
    @PostMapping("/addMeetingRoom/save")
    public String addMeetingRoom(@Valid @ModelAttribute("meetingRoom") MeetingRoomDto meetingRoomDto,
                               BindingResult result,
                               Model model){
        MeetingRoomDto existing = meetingRoomService.getMeetingRoom(meetingRoomDto.getOfficeLocation(),meetingRoomDto.getMeetingRoom());
        if (!existing.getOfficeLocation().isEmpty()) {
            result.rejectValue("meetingRoom", null, "There is already an meeting room added");
        }
        if (result.hasErrors()) {
            model.addAttribute("meetingRoom", meetingRoomDto);
            return "meetingRoom-form";
        }
        meetingRoomService.saveMeetingRoom(meetingRoomDto);
        return "redirect:/meetingRoomManagement";
    }
    
    @GetMapping("/editMeetingRoomForm/{id}") 
    public String showEditMeetingRoomFormForUpdate(@PathVariable ( value = "id") Long id, Model model) {
    	MeetingRoomDto theMeetingRoom = meetingRoomService.getMeetingRoomById(id);
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
        meetingRoomService.saveMeetingRoom(meetingRoomDto);
        return "redirect:/meetingRoomManagement";
    }
}
