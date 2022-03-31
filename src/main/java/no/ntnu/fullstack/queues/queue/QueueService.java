package no.ntnu.fullstack.queues.queue;

import no.ntnu.fullstack.queues.task.Approved;
import no.ntnu.fullstack.queues.task.ApprovedService;
import no.ntnu.fullstack.queues.user.User;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class QueueService {
    private final QueueRepository queueRepository;
    private final ApprovedService approvedService;

    public QueueService(QueueRepository queueRepository, ApprovedService approvedService) {
        this.queueRepository = queueRepository;
        this.approvedService = approvedService;
    }

    /**
     * Get list of all queues for a given course
     * @param courseID the unique id of the course
     * @return list of matching queues
     */
    public Iterable<Queue> getQueue(Long courseID) {
        return queueRepository.findAllByCourseId(courseID);
    }

    /**
     * Add given queue to database
     * @param queue the queue to be added
     * @param user the user sending the request
     * @return the added queue
     */
    public Queue addQueue(Queue queue, User user){
        queue.setUser(user);
        long now = new java.util.Date().getTime();
        queue.setTime(new Date(now));
        return queueRepository.save(queue);
    }

    /**
     * Deletes the given queue
     * @param queue the queue to delete
     */
    public void deleteQueue(Queue queue){
        queueRepository.deleteById(queue.getId());
    }

    /**
     * Approve the queue and delete it
     * @param queue the queue to approve
     * @return the new approval object
     */
    public Approved approveQueue(Queue queue, User assistant){
        Approved approval = approvedService.approveQueue(queue, assistant);
        deleteQueue(queue);
        return approval;
    }

    /**
     * Attach an assistant to a queue
     * @param queue the queue that gets an assistant
     * @param assistant the assistant to attach
     * @return the edited queue object
     */
    public Queue assistQueue(Queue queue, User assistant){
        Queue existingQueue = queueRepository.findById(queue.getId()).orElse(new Queue());
        existingQueue.setAssistant(assistant);
        return queueRepository.save(queue);
    }
}

