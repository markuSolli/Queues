package no.ntnu.fullstack.queues.queue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import no.ntnu.fullstack.queues.course.Course;
import no.ntnu.fullstack.queues.location.Location;
import no.ntnu.fullstack.queues.task.Task;
import no.ntnu.fullstack.queues.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Queue {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @JsonIgnoreProperties({"users", "taskGroups"})
    @ManyToOne
    private Course course;
    @ManyToOne
    private User user;
    private Timestamp time;
    private boolean help;
    @ManyToOne
    private Location room;
    private Integer desk;
    @ManyToOne
    private Task task;
    @ManyToOne
    private User assistant;

    protected Queue() {}

    public Queue(Course course, User user, Timestamp time, boolean help, Location room, Integer desk, Task task) {
        this.course = course;
        this.user = user;
        this.time = time;
        this.help = help;
        this.room = room;
        this.desk = desk;
        this.task =  task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTime() {
        return  time.getTime();
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public Location getRoom() {
        return room;
    }

    public void setRoom(Location room) {
        this.room = room;
    }

    public Integer getDesk() {
        return desk;
    }

    public void setDesk(Integer table) {
        this.desk = table;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getAssistant() {
        return assistant;
    }

    public void setAssistant(User assistant) {
        this.assistant = assistant;
    }
}
