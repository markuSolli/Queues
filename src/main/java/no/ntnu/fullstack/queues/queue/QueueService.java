package no.ntnu.fullstack.queues.queue;

import no.ntnu.fullstack.queues.course.Course;
import org.springframework.stereotype.Service;

@Service
public class QueueService {
    private final QueueRepository queueRepository;

    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    /**
     * Finds all by course
     *
     * @param course course to look for
     * @return all people in queue for that course
     */
    public Iterable<Queue> getQueues(Course course) {
        return queueRepository.findAllByCourse(course);
    }
}

