package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.user.User;

import javax.persistence.*;

@Entity
public class UserCourse {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Course course;
    private CourseRole role;

    protected UserCourse() {}

    public UserCourse(User user, Course course, CourseRole role) {
        this.user = user;
        this.course = course;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CourseRole getRole() {
        return role;
    }

    public void setRole(CourseRole role) {
        this.role = role;
    }
}
