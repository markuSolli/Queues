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

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        logger.info("Creating course " + course.toString() + "...");
        Course addedCourse = courseService.addCourse(course);
        return new ResponseEntity<>(addedCourse, HttpStatus.CREATED);
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
