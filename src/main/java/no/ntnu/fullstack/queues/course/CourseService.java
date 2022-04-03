package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.task.TaskGroup;
import no.ntnu.fullstack.queues.user.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;

    public CourseService(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
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
     * Returns a list of all courses
     *
     * @return list of all courses
     */
    public Iterable<Course> getAll(User user) {
        return courseRepository.findAllByUsers_User(user);
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
        existingCourse.setSeason(course.getSeason());
        existingCourse.setYear(course.getYear());
        existingCourse.setTitle(course.getTitle());
        existingCourse.setActive(course.isActive());
        existingCourse.setArchived(course.isArchived());
        existingCourse.setRooms(course.getRooms());
        return courseRepository.save(existingCourse);
    }

    /**
     * Deletes the given course
     *
     * @param id course to delete
     */
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }


    /**
     * Takes all data about a course and creates a complete network of everything that should be stored
     *
     * @param courseDTO information about a course
     * @return the course that was created from the data
     */
    public Course createCourse(CourseDTO courseDTO) {
        Course course = new Course(courseDTO.getCode(), courseDTO.getTitle(), courseDTO.getSeason(), courseDTO.getYear());

        // Adding all the users to the course with their respective roles
        setUsers(courseDTO, course);

        for(TaskGroup taskGroup : courseDTO.getTaskGroups()) {
            course.getTaskGroups().add(taskGroup);
        }

        return courseRepository.save(course);
    }

    /**
     * Edits course based on the courseDTO object
     *
     * @param courseDTO new data for course
     * @return the edited course
     */
    public Course editCourse(Long id, CourseDTO courseDTO) throws UsernameNotFoundException{
        Course existingCourse = courseRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        existingCourse.setCode(courseDTO.getCode());
        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setSeason(courseDTO.getSeason());
        existingCourse.setYear(courseDTO.getYear());
        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setTaskGroups(courseDTO.getTaskGroups());
        existingCourse.setRooms(courseDTO.getRooms());

        // Edit users
        existingCourse.getUsers().clear();
        setUsers(courseDTO, existingCourse);
        return courseRepository.save(existingCourse);
    }

    /**
     * Makes sure all users are signed up and adds them to the course
     *
     * @param courseDTO data about the users
     * @param existingCourse course to add to
     */
    private void setUsers(CourseDTO courseDTO, Course existingCourse) {
        for(User teacher : courseDTO.getTeachers()) {
            try {
                userService.signup(new UserDTO(teacher.getEmail(), teacher.getFirstName(), teacher.getLastName()), Role.TEACHER);
            } catch (UserAlreadyExistsException e) {
                // good
            }
            existingCourse.addUser(teacher, CourseRole.TEACHER);
        }
        for(User assistant : courseDTO.getAssistants()) {
            try {
                userService.signup(new UserDTO(assistant.getEmail(), assistant.getFirstName(), assistant.getLastName()), Role.ASSISTANT);
            } catch (UserAlreadyExistsException e) {
                // good
            }
            existingCourse.addUser(assistant, CourseRole.ASSISTANT);
        }
        for(User student : courseDTO.getStudents()) {
            try {
                userService.signup(new UserDTO(student.getEmail(), student.getFirstName(), student.getLastName()), Role.STUDENT);
            } catch (UserAlreadyExistsException e) {
                // good
            }
            existingCourse.addUser(student, CourseRole.STUDENT);
        }
    }

    /**
     * Gets all items from the database with the given archived status
     *
     * @param archived get archived or non archived
     * @return list of all items with the given archived status
     */
    public Iterable<Course> getCoursesByArchived(boolean archived) {
        return courseRepository.findAllByArchived(archived);
    }

    /**
     * Gets all items from the database with the given archived status
     *
     * @param archived get archived or non archived
     * @return list of all items with the given archived status
     */
    public Iterable<Course> getCoursesByArchived(boolean archived, User user) {
        return courseRepository.findAllByArchivedAndUsers_User(archived, user);
    }

}
