package io.github.mozzi20.santa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.mozzi20.santa.models.FAQ;

@Repository
public interface FAQRepository extends CrudRepository<FAQ, Integer> {
	
	@Query("SELECT FAQ FROM FAQ FAQ WHERE FAQ.answer IS NOT null AND FAQ.answer IS NOT ''")
	public List<FAQ> findAnswered();
	
}
