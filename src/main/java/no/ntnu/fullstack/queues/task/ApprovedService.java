package no.ntnu.fullstack.queues.task;

import no.ntnu.fullstack.queues.queue.Queue;
import no.ntnu.fullstack.queues.user.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ApprovedService {
    private final ApprovedRepository approvedRepository;

    public ApprovedService(ApprovedRepository approvedRepository) {
        this.approvedRepository = approvedRepository;
    }

    public Iterable<Approved> getAllApproved(User user) {
        return approvedRepository.findAllByUser(user);
    }

    /**
     * Creates a new Approved object from the approval of a queue
     * @param queue the queue to approve
     * @param assistant the user of the assistant approving the queue
     * @return the new approved object
     */
    public Approved approveQueue(Queue queue, User assistant){
        Approved approval = new Approved();
        approval.setTask(queue.getTask());
        approval.setUser(queue.getUser());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        approval.setTime(now);
        approval.setAssistant(assistant);
        return approvedRepository.save(approval);
    }
}
