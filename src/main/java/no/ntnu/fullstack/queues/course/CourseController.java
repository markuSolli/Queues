package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.user.Role;
import no.ntnu.fullstack.queues.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The controller layer for Courses
 *
 */
@RestController
@RequestMapping("/courses")
@CrossOrigin
public class CourseController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Gets all courses
     *
     * @return list of all courses
     */
    @GetMapping
    public ResponseEntity<Iterable<Course>> getAllCourses(@RequestParam("archived") Optional<Boolean> archived, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if(archived.isPresent()) {
            logger.info("Retrieving all courses based on archived...");
            if(user.getRole() == Role.ADMIN) {
                return new ResponseEntity<>(courseService.getCoursesByArchived(archived.get()), HttpStatus.OK);
            }
            return new ResponseEntity<>(courseService.getCoursesByArchived(archived.get(), user), HttpStatus.OK);
        }
        logger.info("Retrieving all courses...");
        if(user.getRole() == Role.ADMIN) {
            return new ResponseEntity<>(courseService.getAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(courseService.getAll(user), HttpStatus.OK);
    }

    /**
     * Gets course with the given id
     *
     * @param id id of course to get
     * @return course with the given id, if there is one.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        try {
            logger.info("Retrieving course {}", id);
            return new ResponseEntity<>(courseService.getCourse(id), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method allows for creation of a course with a large amount of details
     *
     * @param courseDTO information about the course
     * @return course that was created
     */
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CourseDTO courseDTO) {
        logger.info("Creating course {} ...", courseDTO.getCode());
        return new ResponseEntity<>(courseService.createCourse(courseDTO), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> editCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        logger.info("Editing course {} ...", id);
        try {
            return new ResponseEntity<>(courseService.editCourse(id,courseDTO), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long id) {
        logger.info("Deleting course {} ...", id);
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/approved")
    public ResponseEntity<String> getApproved(@PathVariable Long id, Authentication authentication) {
        logger.info("Retrieving all approved courses");
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
