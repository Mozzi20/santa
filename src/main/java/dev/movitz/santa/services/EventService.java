package dev.movitz.santa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.movitz.santa.datatransferobjects.CreateEventDTO;
import dev.movitz.santa.datatransferobjects.CreateQuestionDTO;
import dev.movitz.santa.exceptions.ResourceNotFoundException;
import dev.movitz.santa.models.Event;
import dev.movitz.santa.models.Question;
import dev.movitz.santa.repositories.EventRepository;
import dev.movitz.santa.repositories.QuestionRepository;

@Service
@Transactional
public class EventService {
	
	@Autowired
	private EventRepository eventRepo;
	
	@Autowired
	private QuestionRepository questionRepo;
	
	public Event createEvent(CreateEventDTO createEventDTO) {
		Event event = new Event();
		event.setName(createEventDTO.getName());
		event.setStartDate(createEventDTO.getStartDate());
		event.setWishlistDeadlineDate(createEventDTO.getWishlistDeadlineDate());
		event.setEndDate(createEventDTO.getEndDate());
		return eventRepo.save(event);
	}
	
	public void createQuestion(CreateQuestionDTO createQuestionDTO, Integer eventId) {
		Event event = eventRepo.findById(eventId).orElseThrow(ResourceNotFoundException::new);
		Question question = new Question();
		question.setQuestion(createQuestionDTO.getQuestion());
		event.getQuestions().add(question);
		eventRepo.save(event);
	}
	
	public void deleteQuestion(Integer questionId) {
		questionRepo.deleteById(questionId);
	}
	
	@Transactional(readOnly=true)
	public Optional<Event> getEventById(Integer id) {
		return eventRepo.findById(id);
	}
	
	@Transactional(readOnly=true)
	public Iterable<Event> getEvents() {
		return eventRepo.findAll();
	}

	@Transactional(readOnly=true)
	public Optional<Event> getCurrentEvent() {
		return eventRepo.findCurrent();
	}

}
