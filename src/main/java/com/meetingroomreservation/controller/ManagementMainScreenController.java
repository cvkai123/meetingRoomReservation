package com.meetingroomreservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagementMainScreenController {
    
    @GetMapping("/managementMainScreen")
    public String meetingRoomManagement(Model model){
    	        
        return "mainScreenManagement";
    }
 
}
