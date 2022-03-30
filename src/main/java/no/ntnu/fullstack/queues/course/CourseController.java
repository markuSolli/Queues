package no.ntnu.fullstack.queues.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The controller layer for Courses
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

    @GetMapping
    public ResponseEntity<Iterable<Course>> getAllCourses() {
        logger.info("Retrieving all courses...");
        Iterable<Course> courses = courseService.getAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        try {
            logger.info("Retrieving course " + id + "...");
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
        logger.info("Creating course " + courseDTO.getCode() + "...");
        return new ResponseEntity<>(courseService.createCourse(courseDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Course> editCourse(@RequestBody Course course) {
        logger.info("Editing course " + course.toString() + "...");
        return new ResponseEntity<>(courseService.updateCourse(course), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteCourse(@RequestBody Course course) {
        logger.info("Deleting course " + course.toString() + "...");
        courseService.deleteCourse(course);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
