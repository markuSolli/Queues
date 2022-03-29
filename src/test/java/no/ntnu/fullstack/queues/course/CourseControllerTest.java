package no.ntnu.fullstack.queues.course;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseControllerTest {

    @Autowired
    private CourseController courseController;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(courseController);
    }
}