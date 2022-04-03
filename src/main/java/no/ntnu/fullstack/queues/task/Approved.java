package no.ntnu.fullstack.queues.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import no.ntnu.fullstack.queues.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Approved {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Task task;
    @JsonIgnore
    @ManyToOne
    private User user;
    private Timestamp time;
    @JsonIgnoreProperties("approved")
    @ManyToOne
    private User assistant;

    protected Approved() {}

    public Approved(Task task, User user, Timestamp time, User assistant) {
        this.task = task;
        this.user = user;
        this.time = time;
        this.assistant = assistant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public User getAssistant() {
        return assistant;
    }

    public void setAssistant(User assistant) {
        this.assistant = assistant;
    }
}
