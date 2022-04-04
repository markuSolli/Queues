package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.user.Role;
import no.ntnu.fullstack.queues.user.User;
import no.ntnu.fullstack.queues.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCourseService {
    private final CourseService courseService;
    private final UserService userService;
    private final UserCourseRepository userCourseRepository;

    public UserCourseService(CourseService courseService, UserService userService, UserCourseRepository userCourseRepository) {
        this.courseService = courseService;
        this.userService = userService;
        this.userCourseRepository = userCourseRepository;
    }

    /**
     * Adds the given user to the given course with the given course role
     *
     * @param user user to add, for example a teacher or a student
     * @param course course to add to
     * @param courseRole role of this user in this course. For example, a student can be a student assistant in a
     *                   particular course
     */
    public void addUserToCourse(User user, Course course, CourseRole courseRole) {
        userCourseRepository.save(new UserCourse(user, course, courseRole));
    }

    /**
     * Check if the user has a role in a course
     * @param user the user
     * @param course the course
     * @return the role the user has in a course, or null
     */
    public CourseRole roleInCourse(User user, Course course){
        Optional<UserCourse> optional = userCourseRepository.findByUserAndCourse(user, course);
        return optional.map(UserCourse::getRole).orElse(null);
    }

}
