package no.ntnu.fullstack.queues.queue;

import no.ntnu.fullstack.queues.course.CourseNotFoundException;
import no.ntnu.fullstack.queues.course.CourseService;
import no.ntnu.fullstack.queues.task.Approved;
import no.ntnu.fullstack.queues.task.ApprovedService;
import no.ntnu.fullstack.queues.task.TaskNotFoundException;
import no.ntnu.fullstack.queues.task.TaskService;
import no.ntnu.fullstack.queues.user.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class QueueService {
    private final QueueRepository queueRepository;
    private final ApprovedService approvedService;
    private final CourseService courseService;
    private final TaskService taskService;

    public QueueService(QueueRepository queueRepository, ApprovedService approvedService, CourseService courseService, TaskService taskService) {
        this.queueRepository = queueRepository;
        this.approvedService = approvedService;
        this.courseService = courseService;
        this.taskService = taskService;
    }

    /**
     * Fetches the queue with the given id from the database. Throws QueueNotFoundException if there is none
     *
     * @param id id of queue to find
     * @return queue with given id
     */
    public Queue getQueue(Long id) {
        return queueRepository.findById(id).orElseThrow(() -> new QueueNotFoundException(id));
    }

    /**
     * Get list of all queues for a given course
     * @param courseID the unique id of the course
     * @return list of matching queues
     */
    public Iterable<Queue> getQueueFromCourseId(Long courseID) {
        return queueRepository.findAllByCourseId(courseID);
    }

    /**
     * Add given queue to database
     * @param queue the queue to be added
     * @param user the user sending the request
     * @return the added queue
     */
    public Queue addQueue(Queue queue, User user, Long courseId, Long taskId) throws TaskNotFoundException, CourseNotFoundException {
        queue.setUser(user);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        queue.setTime(now);
        queue.setCourse(courseService.getCourse(courseId));
        queue.setTask(taskService.getTask(taskId));
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
     * @param id id of the queue to approve
     * @return the new approval object
     */
    public Approved approveQueue(Long id, User assistant){
        Queue queue = getQueue(id);
        Approved approval = approvedService.approveQueue(queue, assistant);
        deleteQueue(queue);
        return approval;
    }

    /**
     * Attach an assistant to a queue
     * @param id id of the queue that gets an assistant
     * @param assistant the assistant to attach
     * @return the edited queue object
     */
    public Queue assistQueue(Long id, User assistant){
        Queue existingQueue = queueRepository.findById(id).orElseThrow(() -> new QueueNotFoundException(id));
        existingQueue.setAssistant(assistant);
        return queueRepository.save(existingQueue);
    }
}

