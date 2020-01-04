package dev.movitz.santa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.movitz.santa.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
