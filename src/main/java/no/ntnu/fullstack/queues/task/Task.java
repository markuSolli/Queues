package no.ntnu.fullstack.queues.task;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private int number;

    @ManyToOne(targetEntity = TaskGroup.class)
    private TaskGroup taskGroup;

    protected Task() {}

    public Task(int number, TaskGroup taskGroup) {
        this.number = number;
        this.taskGroup = taskGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TaskGroup getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(TaskGroup taskGroup) {
        this.taskGroup = taskGroup;
    }
}
