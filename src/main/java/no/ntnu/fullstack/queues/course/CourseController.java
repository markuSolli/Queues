package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.authentication.CustomAuthenticationException;
import no.ntnu.fullstack.queues.user.Role;
import no.ntnu.fullstack.queues.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Course> getCourse(@PathVariable Long id, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            logger.info("Retrieving course {}", id);
            return new ResponseEntity<>(courseService.getCourse(id, user), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/progress")
    public ResponseEntity<List<CourseProgress>> getAllProgress(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(courseService.getAllCourseProgress(user), HttpStatus.OK);
    }

    @GetMapping("/progress/{course_id}")
    public ResponseEntity<CourseProgress> getProgress(@PathVariable(name = "course_id") Long courseId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return new ResponseEntity<>(courseService.getCourseProgress(courseId, user), HttpStatus.OK);
        } catch (CourseNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CustomAuthenticationException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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

    /**
     * Updates the course at the given id with the given data
     *
     * @param id id of course
     * @param courseDTO data
     * @return updated course
     */
    @PutMapping("{id}")
    public ResponseEntity<Course> editCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            logger.info("Editing course {} ...", id);
            return new ResponseEntity<>(courseService.editCourse(id, courseDTO, user), HttpStatus.OK);
        } catch (UsernameNotFoundException | CourseNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CustomAuthenticationException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Deletes the course at the given id
     *
     * @param id id of course to delete
     * @return no content
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long id) {
        try {
            logger.info("Deleting course {} ...", id);
            courseService.deleteCourse(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomAuthenticationException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED) ;
        } catch (CourseNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Modifies the archived status of the course with the given status to match the given boolean value
     *
     * @param id id og course to modify
     * @param archived value to set it to
     * @return modified course
     */
    @PatchMapping("/{id}/archived")
    public ResponseEntity<Course> toggleArchived(@PathVariable Long id, @RequestBody Boolean archived, Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            logger.info(archived ? "Archiving course {}..." : "Restoring course {}...", id);
            return new ResponseEntity<>(courseService.toggleArchived(id, archived, user), HttpStatus.OK);
        } catch (CustomAuthenticationException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED) ;
        } catch (CourseNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Modifies the active status of the course with the given status to match the given boolean value
     *
     * @param id id of course to modify
     * @param active value to set it to
     * @return modified course
     */
    @PatchMapping("/{id}/active")
    public ResponseEntity<Course> toggleActive(@PathVariable Long id, @RequestBody Boolean active, Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            logger.info(active ? "Activating course {}..." : "Deactivating course {}...", id);
            return new ResponseEntity<>(courseService.toggleActive(id, active, user), HttpStatus.OK);
        } catch (CustomAuthenticationException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (CourseNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
