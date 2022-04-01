package no.ntnu.fullstack.queues.location;

import no.ntnu.fullstack.queues.course.CourseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/building")
public class BuildingController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping
    public ResponseEntity<Building> addBuilding(@RequestBody Building building){
        logger.info("Adding building " + building.toString() + "...");
        return new ResponseEntity<>(buildingService.addBuilding(building), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteBuilding(@RequestBody Building building){
        logger.info("Deleting building " + building.toString() + "...");
        try{
            buildingService.deleteBuilding(building);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
