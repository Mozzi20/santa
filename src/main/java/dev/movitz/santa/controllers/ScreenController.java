package dev.movitz.santa.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.movitz.santa.models.Event;
import dev.movitz.santa.services.EventService;

@Controller
@RequestMapping("/screen")
public class ScreenController {
	
	@Autowired
	private EventService eventService;
	
	@GetMapping
	public String screen(Model model) {
		Optional<Event> event = eventService.getCurrentEvent();
		model.addAttribute("ongoing", event.isPresent());
		if(event.isPresent()) {
			model.addAttribute("registrationIsOpen", event.get().getWishlistDeadlineDate().after(new Date()));
			model.addAttribute("endDate", event.get().getEndDate());
			model.addAttribute("wishlistDeadlineDate", event.get().getWishlistDeadlineDate());
		}
		return "screen";
	}

}
