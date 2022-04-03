package no.ntnu.fullstack.queues.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByActivation(String activation);
}
