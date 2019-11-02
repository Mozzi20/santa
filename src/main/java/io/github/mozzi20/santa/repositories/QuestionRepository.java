package io.github.mozzi20.santa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.mozzi20.santa.models.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer>{

}
