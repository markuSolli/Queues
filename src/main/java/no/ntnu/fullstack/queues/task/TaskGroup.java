package no.ntnu.fullstack.queues.task;

import no.ntnu.fullstack.queues.course.Course;

import javax.persistence.*;

@Entity
public class TaskGroup {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private int required;
    @ManyToOne(targetEntity = Course.class)
    private Course course;

    protected TaskGroup() {}

    public TaskGroup(int required, Course course) {
        this.required = required;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
