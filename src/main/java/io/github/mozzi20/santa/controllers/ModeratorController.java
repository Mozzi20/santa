package io.github.mozzi20.santa.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.mozzi20.santa.models.Event;
import io.github.mozzi20.santa.models.User;
import io.github.mozzi20.santa.services.EventService;
import io.github.mozzi20.santa.services.WishlistService;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private WishlistService wishlistService;
	
	@GetMapping
	public String index(Model model) {
		Optional<Event> event = eventService.getCurrentEvent();
		if(event.isPresent()) {
			model.addAttribute(event.get());
			return "moderator/index";
		} else {
			return "noevent";
		}
	}
	
	@PostMapping("/wishlist")
	public String userHasGiven(
			@RequestParam Boolean userHasGiven,
			@RequestParam Integer wishlistId
			) {
		wishlistService.setUserHasGiven(userHasGiven, wishlistId);
		return "redirect:/moderator";
	}
	
	@PostMapping("/gift_lookup")
	public String giftLookup(
			@RequestParam Integer wishlistId,
			RedirectAttributes redirect
			) {
		Optional<User> receiver = wishlistService.getGiftReceiver(wishlistId);
		if(receiver.isPresent()) {
			redirect.addFlashAttribute("receiver", receiver.get().getEmail());
		}
		return "redirect:/moderator";
	}
	
}