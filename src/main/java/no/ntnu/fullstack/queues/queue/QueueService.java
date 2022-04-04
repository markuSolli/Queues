package no.ntnu.fullstack.queues.queue;

import no.ntnu.fullstack.queues.authentication.CustomAuthenticationException;
import no.ntnu.fullstack.queues.course.*;
import no.ntnu.fullstack.queues.task.Approved;
import no.ntnu.fullstack.queues.task.ApprovedService;
import no.ntnu.fullstack.queues.task.TaskNotFoundException;
import no.ntnu.fullstack.queues.task.TaskService;
import no.ntnu.fullstack.queues.user.Role;
import no.ntnu.fullstack.queues.user.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class QueueService {
    private final QueueRepository queueRepository;
    private final ApprovedService approvedService;
    private final CourseService courseService;
    private final TaskService taskService;
    private final UserCourseService userCourseService;

    public QueueService(QueueRepository queueRepository, ApprovedService approvedService, CourseService courseService, TaskService taskService, UserCourseService userCourseService) {
        this.queueRepository = queueRepository;
        this.approvedService = approvedService;
        this.courseService = courseService;
        this.taskService = taskService;
        this.userCourseService = userCourseService;
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
    public Queue addQueue(Queue queue, User user, Long courseId, Long taskId) throws TaskNotFoundException, CourseNotFoundException, IllegalArgumentException {
        Course course = courseService.getCourse(courseId);
        if(queueRepository.existsByUserAndTask_TaskGroup_Course(user, course)) throw new IllegalArgumentException();
        CourseRole role = userCourseService.roleInCourse(user, course);
        if(role == null) throw new IllegalArgumentException();
        queue.setUser(user);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        queue.setTime(now);
        queue.setCourse(course);
        queue.setTask(taskService.getTask(taskId));
        return queueRepository.save(queue);
    }

    /**
     * Removes a queue spot from the queue. If the caller is a user, it be their own spot they are removing
     *
     * @param id id of queue to delete
     * @param user caller of removal
     */
    public void removeFromQueue(Long id, User user) {
        Queue queue = getQueue(id);

        if(queue.getCourse().findCourseRole(user) == null && user.getRole() != Role.ADMIN) {
            throw new CustomAuthenticationException("Queues cannot be deleted from users that are not enrolled in the course");
        }
        if(CourseRole.STUDENT.equals(queue.getCourse().findCourseRole(user)) && !queue.getUser().equals(user)) {
            throw new CustomAuthenticationException("Students are not allowed to queues courses that are not their own");
        }

        // If we get here, the user is allowed to perform det deletion
        deleteQueue(id);
    }

    /**
     * Deletes the given queue
     * @param id id of the queue to delete
     */
    public void deleteQueue(Long id){
        queueRepository.deleteById(id);
    }

    /**
     * Approve the queue and delete it
     * @param id id of the queue to approve
     * @return the new approval object
     * @throws IllegalArgumentException
     */
    public Approved approveQueue(Long id, User assistant){
        Course course = getQueue(id).getCourse();
        CourseRole role = userCourseService.roleInCourse(assistant, course);
        if(role == null || role.ordinal() < 1) throw new CustomAuthenticationException("User can only assist in courses they are enrolled in as assistants");
        Queue queue = getQueue(id);
        Approved approval = approvedService.approveQueue(queue, assistant);
        deleteQueue(queue.getId());
        return approval;
    }

    /**
     * Attach an assistant to a queue
     * @param id id of the queue that gets an assistant
     * @param assistant the assistant to attach
     * @return the edited queue object
     * @throws IllegalArgumentException
     */
    public Queue assistQueue(Long id, User assistant){
        Course course = getQueue(id).getCourse();
        CourseRole role = userCourseService.roleInCourse(assistant, course);
        if(role == null || role.ordinal() < 1) throw new IllegalArgumentException();
        if(queueRepository.existsByAssistant(assistant)) throw new IllegalArgumentException("User is already assisting another user");
        Queue existingQueue = getQueue(id);
        existingQueue.setAssistant(assistant);
        return queueRepository.save(existingQueue);
    }
}

