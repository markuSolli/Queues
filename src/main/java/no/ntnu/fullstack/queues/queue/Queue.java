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
    @ManyToOne(targetEntity = Course.class)
    private Course course;
    @ManyToOne
    private User user;
    private Date time;
    private boolean help;
    @ManyToOne(targetEntity = Room.class)
    private Room room;
    private int table;
    @ManyToOne(targetEntity = Task.class)
    private int Task;
    @ManyToOne(targetEntity = User.class)
    private User assistant;

    protected Queue() {}

    public Queue(Course course, User user, Date time, boolean help, Room room, int table, int task) {
        this.course = course;
        this.user = user;
        this.time = time;
        this.help = help;
        this.room = room;
        this.table = table;
        Task = task;
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

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public int getTask() {
        return Task;
    }

    public void setTask(int task) {
        Task = task;
    }

    public User getAssistant() {
        return assistant;
    }

    public void setAssistant(User assistant) {
        this.assistant = assistant;
    }
}
