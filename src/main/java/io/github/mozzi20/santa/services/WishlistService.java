package io.github.mozzi20.santa.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mozzi20.santa.datatransferobjects.WishlistDTO;
import io.github.mozzi20.santa.exceptions.ResourceNotFoundException;
import io.github.mozzi20.santa.models.Event;
import io.github.mozzi20.santa.models.Question;
import io.github.mozzi20.santa.models.User;
import io.github.mozzi20.santa.models.Wishlist;
import io.github.mozzi20.santa.repositories.QuestionRepository;
import io.github.mozzi20.santa.repositories.WishlistRepository;

@Service
@Transactional
public class WishlistService {
	
	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private WishlistRepository wishlistRepo;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userService;
	
	public void saveWishlist(WishlistDTO wishlistDTO) {
		User user = userService.getPersistedUser().get();
		Wishlist wishlist = wishlistRepo.findCurrentWishlistByUser(user.getId()).orElseGet(() -> new Wishlist());
		
		wishlist.setAnswers(new HashMap<Question, String>());
		wishlist.setEvent(eventService.getCurrentEvent().get());
		wishlist.setUser(user);

		wishlistDTO.getAnswers().entrySet()
			.stream()
			.filter(entry -> !entry.getValue().isEmpty())
			.forEach(entry -> {
				Question question = questionRepo.findById(entry.getKey()).get();
				wishlist.getAnswers().put(question, entry.getValue());
			});
		
		if(wishlist.getAnswers().size() == 0) {
			wishlistRepo.delete(wishlist);
		} else {
			wishlistRepo.save(wishlist);			
		}
	}
	
	private void assignSantas(Event event) {
		if(event.getWishlists().size() == 0) return;
		List<Wishlist> wishlists = new ArrayList<Wishlist>(event.getWishlists());
		Wishlist lastWishlist = wishlists.get(wishlists.size() - 1);
		for(Wishlist wishlist : event.getWishlists()) {
			wishlist.setTarget(lastWishlist);
			lastWishlist = wishlist;
		}
		wishlistRepo.saveAll(wishlists);
	}
	
	public Optional<Wishlist> getTargetsWishlist() {
		Event event = eventService.getCurrentEvent().get();
		if(event.getWishlists().stream().allMatch(wl -> wl.getTarget() == null)) {
			assignSantas(event);
		}
		
		String userId = userService.getUserId().get();
		Optional<Wishlist> wishlist = wishlistRepo.findCurrentWishlistByUser(userId);
		if(wishlist.isPresent()) {
			return Optional.ofNullable(wishlist.get().getTarget());
		} else {
			return Optional.empty();
		}
	}
	
	@Transactional(readOnly=true)
	public Optional<Wishlist> getWishlist() {
		return wishlistRepo.findCurrentWishlistByUser(userService.getUserId().get());
	}

	public void setUserHasGiven(Boolean userHasGiven, Integer wishlistId) {
		Wishlist wishlist = wishlistRepo.findById(wishlistId).orElseThrow(ResourceNotFoundException::new);
		wishlist.setUserHasGiven(userHasGiven);
		wishlistRepo.save(wishlist);
	}
	
	@Transactional(readOnly=true)
	public Optional<Wishlist> getWishlistById(Integer wishlistId) {
		return wishlistRepo.findById(wishlistId);
	}
	
	@Transactional(readOnly=true)
	public Optional<User> getGiftReceiver(Integer wishlistId) {
		Optional<Wishlist> giftersWishlist = getWishlistById(wishlistId);
		if(!giftersWishlist.isPresent()) {
			return Optional.empty();
		}
		
		Wishlist target = giftersWishlist.get().getTarget();
		
		while(!target.getUserHasGiven() && target.getId() != giftersWishlist.get().getId()) {
			target = target.getTarget();
			if(target == null) {
				throw new IllegalStateException("santa chain is broken");
			} 
		}
		
		return Optional.of(target.getUser());
		
	}

}
