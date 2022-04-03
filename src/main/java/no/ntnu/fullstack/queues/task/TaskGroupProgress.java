package no.ntnu.fullstack.queues.task;

import java.util.List;

public class TaskGroupProgress {
    private Long id;
    private int number;
    private int required;
    private int completed;
    private List<TaskProgress> taskProgress;

    public TaskGroupProgress() {
    }

    public TaskGroupProgress(Long id, int number, int required) {
        this.id = id;
        this.number = number;
        this.required = required;
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

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public List<TaskProgress> getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(List<TaskProgress> taskProgress) {
        this.taskProgress = taskProgress;
    }
}

