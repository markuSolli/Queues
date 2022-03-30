package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.user.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCourseController {
    private final UserCourseService userCourseService;

    public UserCourseController(UserCourseService userCourseService) {
        this.userCourseService = userCourseService;
    }

//    @PostMapping("courses/complete")
//    public UserCourse addUserToCourse(@RequestBody UserCourse userCourse) {
//        return userCourseService.addUserCourse(userCourse);
//    }
}
