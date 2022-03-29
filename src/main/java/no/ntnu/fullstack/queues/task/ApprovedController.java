package no.ntnu.fullstack.queues.task;

import no.ntnu.fullstack.queues.course.CourseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/approvals")
public class ApprovedController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final ApprovedService approvedService;

    public ApprovedController(ApprovedService approvedService) {
        this.approvedService = approvedService;
    }
}
