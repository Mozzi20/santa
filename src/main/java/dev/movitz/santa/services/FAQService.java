package dev.movitz.santa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.movitz.santa.datatransferobjects.FAQDTO;
import dev.movitz.santa.exceptions.ResourceNotFoundException;
import dev.movitz.santa.models.FAQ;
import dev.movitz.santa.repositories.FAQRepository;

@Service
@Transactional
public class FAQService {
	
	@Autowired
	private FAQRepository FAQRepo;
	
	public void createFAQ(FAQDTO FAQDTO) {
		FAQ FAQ = new FAQ();
		FAQ.setQuestion(FAQDTO.getQuestion());
		FAQRepo.save(FAQ);
	}
	
	@Transactional(readOnly=true)
	public List<FAQ> getAnswerdFAQs() {
		return FAQRepo.findAnswered();
	}
	
	@Transactional(readOnly=true)
	public Iterable<FAQ> getAllFAQs() {
		return FAQRepo.findAll();
	}

	public void answerFAQ(String answer, Integer FAQId) {
		FAQ FAQ = FAQRepo.findById(FAQId).orElseThrow(ResourceNotFoundException::new);
		FAQ.setAnswer(answer);
		FAQRepo.save(FAQ);
	}

}
