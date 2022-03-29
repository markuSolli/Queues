package no.ntnu.fullstack.queues.queue;

import no.ntnu.fullstack.queues.course.CourseController;
import no.ntnu.fullstack.queues.task.Approved;
import no.ntnu.fullstack.queues.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/queues")
public class QueueController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Iterable<Queue>> getQueue(@PathVariable Long id){
        try{
            logger.info("Retrieving queue for course " + id + "...");
            return new ResponseEntity<>(queueService.getQueue(id), HttpStatus.OK);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Queue> addQueue(@RequestBody Queue queue){
        logger.info("Creating queue " + queue.toString() + "...");
        User user = queue.getUser(); //TODO: Get logged in user
        Queue addedQueue = queueService.addQueue(queue, user);
        return new ResponseEntity<>(addedQueue, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteQueue(@RequestBody Queue queue){
        logger.info("Deleting queue " + queue.toString() + "...");
        queueService.deleteQueue(queue);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Approved> approveQueue(@RequestBody Queue queue){
        logger.info("Approving queue " + queue.toString() + "...");
        Approved addedAproval = queueService.approveQueue(queue, queue.getAssistant());
        return new ResponseEntity<>(addedAproval, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Queue> assistQueue(@RequestBody Queue queue){
        logger.info("Attaching assistant to queue " + queue + "...");
        User assistant = queue.getAssistant(); //TODO: Get logged in user
        return new ResponseEntity<>(queueService.assistQueue(queue, assistant), HttpStatus.OK);
    }
}
