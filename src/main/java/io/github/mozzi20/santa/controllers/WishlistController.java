package io.github.mozzi20.santa.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.mozzi20.santa.datatransferobjects.WishlistDTO;
import io.github.mozzi20.santa.models.Event;
import io.github.mozzi20.santa.models.Wishlist;
import io.github.mozzi20.santa.services.EventService;
import io.github.mozzi20.santa.services.WishlistService;

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
		
		if(event.get().getWishlistDeadlineDate().after(new Date())) { // user is able to update wishlist
			wishlistService.getWishlist().ifPresent(model::addAttribute);
			return "wishlist";
		} else {
			Optional<Wishlist> wishlist = wishlistService.getTargetsWishlist();
			if(!wishlist.isPresent()) {
				return "toolate";
			}
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
