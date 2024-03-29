package com.meetingroomreservation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.meetingroomreservation.dto.MeetingRoomDto;
import com.meetingroomreservation.service.MeetingRoomService;

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
    
    @GetMapping("/showForm")
    public String showFormForAdd(Model theModel) {
    	MeetingRoomDto theMeetingRoom = new MeetingRoomDto();
        theModel.addAttribute("meetingRoom", theMeetingRoom);
        return "meetingRoom-form";
    }

	/*
	 * @PostMapping("/saveMeetingRoom") public String
	 * saveMeetingRoom(@ModelAttribute("MeetingRoom") MeetingRoom meetingRoom) {
	 * meetingRoomService.saveMeetingRoom(meetingRoom); return
	 * "redirect:/meetingRoom"; }
	 * 
	 * @GetMapping("/updateForm") public String
	 * showFormForUpdate(@RequestParam("meetingRoomId") int theId, Model theModel) {
	 * MeetingRoom theMeetingRoom = meetingRoomService.getMeetingRoom(theId);
	 * theModel.addAttribute("meetingRoom", theMeetingRoom); return
	 * "meetingRoom-form"; }
	 * 
	 * @GetMapping("/delete") public String
	 * deleteMeetingRoom(@RequestParam("meetingRoomId") int theId) {
	 * meetingRoomService.deleteMeetingRoom(theId); return "redirect:/meetingRoom";
	 * }
	 */

}
