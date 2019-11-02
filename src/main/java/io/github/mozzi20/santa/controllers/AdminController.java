package io.github.mozzi20.santa.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.mozzi20.santa.datatransferobjects.CreateEventDTO;
import io.github.mozzi20.santa.datatransferobjects.CreateQuestionDTO;
import io.github.mozzi20.santa.exceptions.ResourceNotFoundException;
import io.github.mozzi20.santa.models.Event;
import io.github.mozzi20.santa.services.EventService;
import io.github.mozzi20.santa.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String index() {
		return "admin/index";
	}
	
	@GetMapping("/events")
	public String events(Model model) {
		model.addAttribute(new CreateEventDTO());
		model.addAttribute("events", eventService.getEvents());
		return "admin/events";
	}
	
	@PostMapping("/events")
	public String createEvent(
			@Valid CreateEventDTO createEventDTO,
			Errors errors,
			RedirectAttributes redirect
			) {
		if(errors.hasErrors()) {
			redirect.addFlashAttribute("errors", errors);
			return "redirect:/admin/events";
		} else {
			Event event = eventService.createEvent(createEventDTO);
			redirect.addAttribute("eventId", event.getId());
			return "redirect:/admin/events/{eventId}";
		}
	}
	
	@GetMapping("/events/{eventId}")
	public String event(
			@PathVariable Integer eventId,
			Model model
			) {
		Event event = eventService.getEventById(eventId).orElseThrow(ResourceNotFoundException::new);
		model.addAttribute(event);
		model.addAttribute(new CreateQuestionDTO());
		return "admin/event";
	}
	
	@GetMapping("/events/{eventId}/wishlists")
	public String wishlists(
			@PathVariable Integer eventId,
			Model model
			) {
		Event event = eventService.getEventById(eventId).orElseThrow(ResourceNotFoundException::new);
		model.addAttribute(event);
		return "admin/wishlists";
	}
	
	@PostMapping("/events/{eventId}/questions")
	public String createQuestion(
			@PathVariable Integer eventId,
			@Valid CreateQuestionDTO createQuestionDTO,
			Errors errors,
			RedirectAttributes redirect
			) {
		if(errors.hasErrors()) {
			redirect.addFlashAttribute("errors", errors);
		} else {
			eventService.createQuestion(createQuestionDTO, eventId);
		}
		redirect.addAttribute("eventId", eventId);
		return "redirect:/admin/events/{eventId}";
	}
	
	@PostMapping("/events/{eventId}/questions/{questionId}/delete")
	public String deleteQuestion(
			@PathVariable Integer questionId,
			@PathVariable Integer eventId,
			RedirectAttributes redirect
			) {
		eventService.deleteQuestion(questionId);
		redirect.addAttribute("eventId", eventId);
		return "redirect:/admin/events/{eventId}";
	}
	
	@GetMapping("/users")
	public String users(
			Model model
			) {
		model.addAttribute("users", userService.getAllUsers());
		return "admin/users";
	}

}
