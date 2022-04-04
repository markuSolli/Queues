package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.authentication.CustomAuthenticationException;
import no.ntnu.fullstack.queues.task.*;
import no.ntnu.fullstack.queues.user.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final ApprovedService approvedService;

    public CourseService(CourseRepository courseRepository, UserService userService, ApprovedService approvedService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.approvedService = approvedService;
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
     * Returns the course with the given id, if the user asking for it is in the course
     * @param id id of course to look for
     * @param user loggen in user
     * @return course, if the user is allowed to
     */
    public Course getCourse(Long id, User user) {
        Course course = getCourse(id);
        if(!course.isUserInCourse(user) && !user.isAdmin()) {
            throw new CustomAuthenticationException("A user can only access a course the user is enrolled in");
        }
        return course;
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
     * Deletes the given course if the user is allowed to do so
     *
     * @param id course to delete
     * @param caller caller of method
     */
    public void deleteCourse(Long id, User caller) {
        Course course = getCourse(id);
        if(course.findCourseRole(caller) != CourseRole.TEACHER && !caller.isAdmin()) {
            throw new CustomAuthenticationException("A user must be a teacher in the course to delete it");
        }
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

        course.setTaskGroups(courseDTO.getTaskGroups());

        course.setRooms(courseDTO.getRooms());

        return courseRepository.save(course);
    }

    /**
     * Edits course based on the courseDTO object
     *
     * @param courseDTO new data for course
     * @return the edited course
     */
    public Course editCourse(Long id, CourseDTO courseDTO, User caller) throws UsernameNotFoundException{
        Course existingCourse = courseRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        if(existingCourse.findCourseRole(caller) != CourseRole.TEACHER && !caller.isAdmin()) {
            throw new CustomAuthenticationException("A user must be a teacher in the course to edit it");
        }

        existingCourse.getUsers().clear();
        setUsers(courseDTO, existingCourse);

        existingCourse.setCode(courseDTO.getCode());
        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setSeason(courseDTO.getSeason());
        existingCourse.setYear(courseDTO.getYear());
        existingCourse.setTaskGroups(courseDTO.getTaskGroups());
        existingCourse.setRooms(courseDTO.getRooms());

        // Edit users
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


    /**
     * Changes the state flag of a course into the given value if the user calling the method is allowed to do so
     *
     * @param id id of course to update
     * @param archived new state
     * @param caller caller of method
     * @return edited course
     */
    public Course toggleArchived(Long id, boolean archived, User caller) {
        Course course = getCourse(id);
        if(course.findCourseRole(caller) != CourseRole.TEACHER && !caller.isAdmin()) {
            throw new CustomAuthenticationException("Users can only archive and restore courses they are teachers in");
        }
        course.setArchived(archived);
        return courseRepository.save(course);
    }

    /**
     * Changes the active state of a course into the given value
     * @param id id of course to update
     * @param active new state
     * @return edited course
     */
    public Course toggleActive(Long id, boolean active, User caller) {
        Course course = getCourse(id);
        if(course.findCourseRole(caller) != CourseRole.ASSISTANT && !caller.isAdmin()) {
            throw new CustomAuthenticationException("Users can only activate and deactivate courses they are assistants in");
        }
        course.setActive(active);
        return courseRepository.save(course);
    }

    /**
     * Returns a list of current progress of all courses for a user
     * @param user
     * @return
     */
    public List<CourseProgress> getAllCourseProgress(User user) {
        List<CourseProgress> progress = new ArrayList<>();
        Iterable<Course> courses = courseRepository.findAllByUsers_User(user);
        Iterable<Approved> allApproved = approvedService.getAllApproved(user);
        for(Course course : courses) {
            CourseProgress courseProgress = new CourseProgress();
            courseProgress.setId(course.getId());
            courseProgress.setCode(course.getCode());
            courseProgress.setTitle(course.getTitle());
            courseProgress.setSeason(course.getSeason());
            courseProgress.setYear(course.getYear());
            courseProgress.setActive(course.isActive());
            courseProgress.setArchived(course.isArchived());
            courseProgress.setTaskGroupProgress(calculateProgress(course, allApproved));
            if(!courseProgress.isArchived()) {
                progress.add(courseProgress);
            }
        }
        return progress;
    }

    /**
     * Fetches the progress related to one specific course
     *
     * @param courseId id of course to check for
     * @param user which users progress
     * @return progress of user in course
     */
    public CourseProgress getCourseProgress(Long courseId, User user) {
        CourseProgress courseProgress = new CourseProgress();
        Course course = getCourse(courseId);
        Iterable<Approved> allApproved = approvedService.getAllApproved(user);
        courseProgress.setId(course.getId());
        courseProgress.setCode(course.getCode());
        courseProgress.setTitle(course.getTitle());
        courseProgress.setSeason(course.getSeason());
        courseProgress.setYear(course.getYear());
        courseProgress.setActive(course.isActive());
        courseProgress.setArchived(course.isArchived());
        courseProgress.setTaskGroupProgress(calculateProgress(course, allApproved));
        return courseProgress;
    }

    /**
     * Helper method for calculation which tasks are approved in a course based on all approvals
     *
     * @param course course
     * @param approvals approved tasks
     * @return list of all tasks in the course, with a boolean indication whether they are approved or not
     */
    private List<TaskGroupProgress> calculateProgress(Course course, Iterable<Approved> approvals) {
        List<TaskGroupProgress> taskGroupProgress = new ArrayList<>();
        for(TaskGroup taskGroup : course.getTaskGroups()) {
            int completed = 0;
            TaskGroupProgress taskGroupProgressItem = new TaskGroupProgress(taskGroup.getId(),taskGroup.getNumber(), taskGroup.getRequired());
            List<TaskProgress> taskProgress = new ArrayList<>();
            for(Task task: taskGroup.getTasks()) {
                boolean approved = false;
                for(Approved approvedItem : approvals) {
                    if(task.getId() == approvedItem.getTask().getId()) {
                        // Task is approved!
                        completed++;
                        approved = true;
                    }
                }
                taskProgress.add(new TaskProgress(task.getId(), task.getNumber(), approved));
            }
            taskGroupProgressItem.setCompleted(completed);
            taskProgress.sort(Comparator.comparingInt(TaskProgress::getNumber));
            taskGroupProgressItem.setTaskProgress(taskProgress);
            taskGroupProgress.add(taskGroupProgressItem);
        }
        taskGroupProgress.sort(Comparator.comparingInt(TaskGroupProgress::getNumber));
        return taskGroupProgress;
    }

}
