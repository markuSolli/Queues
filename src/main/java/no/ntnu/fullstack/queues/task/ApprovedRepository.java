package no.ntnu.fullstack.queues.task;

import no.ntnu.fullstack.queues.user.User;
import org.springframework.data.repository.CrudRepository;

public interface ApprovedRepository extends CrudRepository<Approved, Long> {

    Iterable<Approved> findAllByUser(User user);

}
