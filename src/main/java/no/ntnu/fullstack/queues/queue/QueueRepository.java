package no.ntnu.fullstack.queues.queue;

import no.ntnu.fullstack.queues.course.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends CrudRepository<Queue, Long> {

    Iterable<Queue> findAllByCourse(Course course);
}
