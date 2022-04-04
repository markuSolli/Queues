package no.ntnu.fullstack.queues.queue;

import no.ntnu.fullstack.queues.course.Course;
import no.ntnu.fullstack.queues.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends CrudRepository<Queue, Long> {

    Iterable<Queue> findAllByCourseId(Long courseID);
    boolean existsByUserAndTask_TaskGroup_Course(User user, Course course);
    boolean existsByAssistant(User assistant);
}
