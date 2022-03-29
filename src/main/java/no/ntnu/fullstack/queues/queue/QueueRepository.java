package no.ntnu.fullstack.queues.queue;

import no.ntnu.fullstack.queues.course.Course;
import org.springframework.data.repository.CrudRepository;

public interface QueueRepository extends CrudRepository<Queue, Long> {

    Iterable<Queue> findAllByCourse(Course course);
}
