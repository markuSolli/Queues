package no.ntnu.fullstack.queues.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Long, User> {
    Optional<User> findByEmail(String username);
}
