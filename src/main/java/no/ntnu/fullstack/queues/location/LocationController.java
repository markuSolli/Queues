package no.ntnu.fullstack.queues.location;

import no.ntnu.fullstack.queues.course.CourseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/location")
public class LocationController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody Location location){
        logger.info("Adding location " + location.getTitle() + "...");
        return new ResponseEntity<>(locationService.addLocation(location), HttpStatus.CREATED);
    }
}
