package no.ntnu.fullstack.queues.queue;

import no.ntnu.fullstack.queues.course.CourseController;
import no.ntnu.fullstack.queues.course.CourseNotFoundException;
import no.ntnu.fullstack.queues.task.Approved;
import no.ntnu.fullstack.queues.task.TaskNotFoundException;
import no.ntnu.fullstack.queues.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/queue")
public class QueueController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Iterable<Queue>> getQueue(@PathVariable Long id){
        try{
            logger.info("Retrieving queue for course " + id + "...");
            return new ResponseEntity<>(queueService.getQueueFromCourseId(id), HttpStatus.OK);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{course_id}/{task_id}")
    public ResponseEntity<Queue> addQueue(@PathVariable("course_id") Long courseId, @PathVariable("task_id") Long taskId, @RequestBody Queue queue, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        logger.info("Adding {} to course {} for {} on task {}", user.getFirstName(), courseId, queue.isHelp() ? "help" : "approval", taskId);
        try {
            Queue addedQueue = queueService.addQueue(queue, user, courseId, taskId);
            return new ResponseEntity<>(addedQueue, HttpStatus.CREATED);
        } catch (TaskNotFoundException | CourseNotFoundException | IllegalArgumentException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteQueue(@RequestBody Queue queue){
        logger.info("Deleting queue " + queue.toString() + "...");
        queueService.deleteQueue(queue);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Approved> approveQueue(@PathVariable Long id, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        logger.info("Approving queue {}...", id);
        try {
            return new ResponseEntity<>(queueService.approveQueue(id, user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/assist")
    public ResponseEntity<Queue> assistQueue(@PathVariable Long id, Authentication authentication){
        logger.info("Attaching assistant to queue {}...", id);
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(queueService.assistQueue(id, user), HttpStatus.OK);
    }
}
