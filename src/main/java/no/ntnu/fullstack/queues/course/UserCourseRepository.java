package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserCourseRepository extends CrudRepository<UserCourse, Long> {

    Optional<UserCourse> findByUserAndCourse(User user, Course course);
}
