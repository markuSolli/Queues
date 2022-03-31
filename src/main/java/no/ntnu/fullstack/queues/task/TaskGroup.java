package no.ntnu.fullstack.queues.task;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TaskGroup {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private int required;
    private int number;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Task> tasks;

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

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
