package no.ntnu.fullstack.queues.queue;

import no.ntnu.fullstack.queues.course.Course;
import no.ntnu.fullstack.queues.location.Room;
import no.ntnu.fullstack.queues.task.Task;
import no.ntnu.fullstack.queues.user.User;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Queue {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Course course;
    @ManyToOne
    private User user;
    private Date time;
    private boolean help;
    @ManyToOne
    private Room room;
    private Integer desk;
    @ManyToOne(targetEntity = Task.class)
    private Task task;
    @ManyToOne(targetEntity = User.class)
    private User assistant;

    protected Queue() {}

    public Queue(Course course, User user, Date time, boolean help, Room room, Integer desk, Task task) {
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
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
