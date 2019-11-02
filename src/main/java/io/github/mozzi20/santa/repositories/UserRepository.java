package io.github.mozzi20.santa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.mozzi20.santa.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
