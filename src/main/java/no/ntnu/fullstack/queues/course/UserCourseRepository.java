package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserCourseRepository extends CrudRepository<UserCourse, Long> {

    boolean existsByUserAndCourse(User user, Course course);
}
