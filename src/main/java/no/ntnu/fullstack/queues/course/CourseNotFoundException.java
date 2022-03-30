package no.ntnu.fullstack.queues.course;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(Long id) {
        super("Couldn't find course " + id);
    }

    public CourseNotFoundException(String code) {
        super("Couldn't find course " + code);
    }
}
