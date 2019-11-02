package io.github.mozzi20.santa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.mozzi20.santa.models.Wishlist;

@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {

	@Query("SELECT wishlist FROM Wishlist wishlist WHERE wishlist.user.id = :userId AND wishlist.event.startDate < CURRENT_TIMESTAMP AND wishlist.event.endDate > CURRENT_TIMESTAMP")
	public Optional<Wishlist> findCurrentWishlistByUser(String userId);

	@Query("SELECT wishlist FROM Wishlist wishlist WHERE wishlist.santa.id = :userId AND wishlist.event.startDate < CURRENT_TIMESTAMP AND wishlist.event.endDate > CURRENT_TIMESTAMP")
	public Optional<Wishlist> findCurrentTargetsWishlistByUser(String userId);

}
