package no.ntnu.fullstack.queues.queue;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends CrudRepository<Queue, Long> {

    Iterable<Queue> findAllByCourseId(Long courseID);
}
