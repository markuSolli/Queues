package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Returns a list of all courses
     *
     * @return list of all courses
     */
    public Iterable<Course> getAll() {
        return courseRepository.findAll();
    }

    /**
     * Returns the course with the given id
     *
     * @param id id of the course
     * @return the course
     */
    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    /**
     * Adds the given course to the database
     *
     * @return the course that was added
     */
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Updates a course with the given course data
     *
     * @param course course with new data
     * @return edited course
     */
    public Course updateCourse(Course course) {
        Course existingCourse = courseRepository.findById(course.getId()).orElse(new Course());
        existingCourse.setId(course.getId());
        existingCourse.setCode(course.getCode());
        existingCourse.setTitle(course.getTitle());
        existingCourse.setStartDate(course.getStartDate());
        existingCourse.setEndDate(course.getEndDate());
        existingCourse.setTitle(course.getTitle());
        existingCourse.setActive(course.isActive());
        existingCourse.setArchived(course.isArchived());
        existingCourse.setRooms(course.getRooms());
        return courseRepository.save(existingCourse);
    }

    /**
     * Deletes the given course
     *
     * @param course course to delete
     */
    public void deleteCourse(Course course) {
        courseRepository.deleteById(course.getId());
    }


    /**
     * Takes all data about a course and creates a complete network of everything that should be stored
     */
    public Course createCourse(CourseDTO courseDTO) {
        Course course = new Course(courseDTO.getCode(), courseDTO.getTitle(), courseDTO.getStartDate(), courseDTO.getEndDate());

        // Adding all the users to the course with their respective roles
        for(User teacher : courseDTO.getTeachers()) {
            course.addUser(teacher, CourseRole.TEACHER);
        }
        for(User assistant : courseDTO.getAssistants()) {
            course.addUser(assistant, CourseRole.ASSISTANT);
        }
        for(User student : courseDTO.getStudents()) {
            course.addUser(student, CourseRole.STUDENT);
        }

        return courseRepository.save(course);
    }

}
