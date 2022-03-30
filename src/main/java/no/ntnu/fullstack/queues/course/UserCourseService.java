package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.user.User;
import no.ntnu.fullstack.queues.user.UserService;
import org.springframework.stereotype.Service;

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
     * Adds a User - Course relationship to the database
     *
     * @param userCourse user course to add
     * @return UserCourse
     */
    public UserCourse addUserCourse(UserCourse userCourse) {
        User user = (User) userService.loadUserByUsername(userCourse.getUser().getUsername());
        Course course = courseService.findCourse(userCourse.getCourse().getCode());
        return userCourseRepository.save(userCourse);
    }


}
