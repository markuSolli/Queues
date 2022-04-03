package no.ntnu.fullstack.queues.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ApprovedController {
    private static final Logger logger = LoggerFactory.getLogger(ApprovedController.class);

    private final ApprovedService approvedService;

    public ApprovedController(ApprovedService approvedService) {
        this.approvedService = approvedService;
    }
}
