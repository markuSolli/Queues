package no.ntnu.fullstack.queues.location;

import no.ntnu.fullstack.queues.course.CourseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Deprecated
@RestController
@CrossOrigin
@RequestMapping("/campus")
public class CampusController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final CampusService campusService;

    public CampusController(CampusService campusService) {
        this.campusService = campusService;
    }

    @PostMapping
    public ResponseEntity<Campus> addCampus(@RequestBody Campus campus){
        logger.info("Adding campus " + campus.toString() + "...");
        return new ResponseEntity<>(campusService.addCampus(campus), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteCampus(@RequestBody Campus campus){
        logger.info("Deleting campus " + campus.toString() + "...");
        try{
            campusService.deleteCampus(campus);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
