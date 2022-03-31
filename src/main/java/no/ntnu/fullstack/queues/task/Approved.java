package no.ntnu.fullstack.queues.task;

import no.ntnu.fullstack.queues.user.User;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Approved {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Task task;
    @ManyToOne
    private User user;
    private Date date;
    @ManyToOne
    private User assistant;

    protected Approved() {}

    public Approved(Task task, User user, Date date, User assistant) {
        this.task = task;
        this.user = user;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAssistant() {
        return assistant;
    }

    public void setAssistant(User assistant) {
        this.assistant = assistant;
    }
}
