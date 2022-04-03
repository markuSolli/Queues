package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Optional<Course> findCourseByCode(String code);
    Iterable<Course> findAllByArchived(boolean archived);
    Iterable<Course> findAllByArchivedAndUsers_User(boolean archived, User user);
    Iterable<Course> findAllByUsers_User(User user);

}
