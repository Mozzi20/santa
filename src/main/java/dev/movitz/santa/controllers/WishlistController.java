package dev.movitz.santa.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.movitz.santa.datatransferobjects.WishlistDTO;
import dev.movitz.santa.models.Event;
import dev.movitz.santa.models.Wishlist;
import dev.movitz.santa.services.EventService;
import dev.movitz.santa.services.WishlistService;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private WishlistService wishlistService;
	
	@GetMapping
	public String index(Model model) {
		Optional<Event> event = eventService.getCurrentEvent();
		if(!event.isPresent()) {
			return "noevent";
		}
		
		model.addAttribute(event.get());
		Optional<Wishlist> usersWishlist = wishlistService.getWishlist();
		if(event.get().getWishlistDeadlineDate().after(new Date())) {
			usersWishlist.ifPresent(model::addAttribute);
			return "wishlist";
		} else {
			Optional<Wishlist> wishlist = wishlistService.getTargetsWishlist();
			if(!wishlist.isPresent()) {
				return "toolate";
			}
			model.addAttribute("usersWishlistId", usersWishlist.get().getId());
			model.addAttribute("wishlist", wishlist.get());
			return "targetsWishlist";
		}
	}
	
	@PostMapping
	public String saveWishlist(WishlistDTO wishlistDTO) {
		wishlistService.saveWishlist(wishlistDTO);
		return "redirect:/wishlist";
	}

}
