package no.ntnu.fullstack.queues.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    public void setup(){
        Course right = new Course("IFYT1001","Fysikk",Season.SPRING,2022);
        right.setId(1L);
        Course wrong = new Course("WRNG404","Wrong",Season.SPRING,2022);
        wrong.setId(2L);

        // getCourse
        Mockito.lenient().when(courseRepository.findById(right.getId()))
                .thenReturn(Optional.of(right));
        Mockito.lenient().when(courseRepository.findById(right.getId()))
                .thenReturn(Optional.of(right));
    }

    @Nested
    class getCourse{

        @Test
        public void valid_id_finds_course(){
            Long valid_id = 1L;
            Course course = courseService.getCourse(valid_id);

            assertEquals(valid_id, course.getId());
        }

        @Test
        public void wrong_id_throws_error(){
            Long wrong_id = 2L;
            try{
                Course course = courseService.getCourse(wrong_id);
                fail();
            }catch(CourseNotFoundException ignored){
            }
        }
    }
}
