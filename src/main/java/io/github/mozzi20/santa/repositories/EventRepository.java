package io.github.mozzi20.santa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.mozzi20.santa.models.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
	
	@Query("SELECT event FROM Event event WHERE event.startDate < CURRENT_TIMESTAMP AND event.endDate > CURRENT_TIMESTAMP")
	public Optional<Event> findCurrent();

}
