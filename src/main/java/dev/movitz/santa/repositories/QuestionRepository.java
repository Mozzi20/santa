package dev.movitz.santa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.movitz.santa.models.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer>{

}
