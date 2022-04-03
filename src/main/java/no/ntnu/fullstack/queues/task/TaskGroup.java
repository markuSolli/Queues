package no.ntnu.fullstack.queues.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.ntnu.fullstack.queues.course.Course;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TaskGroup {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private int required;
    private int number;
    @JsonIgnore
    @ManyToOne
    private Course course;
    @OneToMany(mappedBy = "taskGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Task> tasks = new HashSet<>();

    protected TaskGroup() {}

    public TaskGroup(int required) {
        this.required = required;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
        if(tasks == null) {
            return;
        }
        this.tasks.clear();
        for(Task task : tasks) {
            task.setTaskGroup(this);
        }
        this.tasks.addAll(tasks);
    }

    @Override
    public String toString() {
        return "TaskGroup{" +
                "id=" + id +
                ", required=" + required +
                ", number=" + number +
                ", tasks=" + tasks +
                '}';
    }
}
