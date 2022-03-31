package no.ntnu.fullstack.queues.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.ntnu.fullstack.queues.course.Course;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TaskGroup {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private int required;
    @JsonIgnore
    @ManyToOne(targetEntity = Course.class, cascade = CascadeType.ALL)
    private Course course;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Task> tasks;

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

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
