package com.meetingroomreservation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.meetingroomreservation.dto.LocationDto;
import com.meetingroomreservation.service.LocationService;

import jakarta.validation.Valid;

@Controller
public class LocationController {

	private LocationService locationService;

	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}

	@GetMapping("/locationManagement")
	public String locationManagement(Model model) {
		List<LocationDto> locations = locationService.findAllOfficeLocation();
		model.addAttribute("locations", locations);
		return "location";
	}
	
	@GetMapping("/showLocationForm")
	public String showLocationFormForAdd(Model theModel) {
		LocationDto theLocation = new LocationDto();
		theModel.addAttribute("location", theLocation);
		return "location-form";
	}

	// handler method to handle add location form submit request
	@PostMapping("/addLocation/save")
	public String addLocation(@Valid @ModelAttribute("location") LocationDto locationDto, BindingResult result,
			Model model) {
		LocationDto existing = locationService.getOfficeLocation(locationDto.getOfficeLocation());
		if (existing.getOfficeLocation() != null) {
			result.rejectValue("officeLocation", null, "There is already an location added");
		}
		if (result.hasErrors()) {
			model.addAttribute("location", locationDto);
			return "location-form";
		}
		locationService.saveOfficeLocation(locationDto);
		return "redirect:/locationManagement";
	}

	@GetMapping("/editLocationForm/{id}")
	public String showEditLocationFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
		LocationDto theLocation = locationService.getOfficeLocationById(id);
		model.addAttribute("location", theLocation);
		return "location-editform";
	}

	@GetMapping("/deleteLocationForm/{id}")
	public String deleteLocation(@PathVariable(value = "id") Long id, Model model) {
		locationService.deleteOfficeLocation(id);
		return "redirect:/locationManagement";
	}

	@PostMapping("/updateLocation/save")
	public String updateLocation(@Valid @ModelAttribute("location") LocationDto locationDto, BindingResult result,
			Model model) {
		locationService.saveOfficeLocation(locationDto);
		return "redirect:/locationManagement";
	}
}
