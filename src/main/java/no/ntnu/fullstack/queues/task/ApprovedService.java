package no.ntnu.fullstack.queues.task;

import no.ntnu.fullstack.queues.queue.Queue;
import no.ntnu.fullstack.queues.user.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class ApprovedService {
    private final ApprovedRepository approvedRepository;

    public ApprovedService(ApprovedRepository approvedRepository) {
        this.approvedRepository = approvedRepository;
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
        approval.setDate(Date.valueOf(LocalDate.now()));
        approval.setAssistant(assistant);
        return approvedRepository.save(approval);
    }
}
