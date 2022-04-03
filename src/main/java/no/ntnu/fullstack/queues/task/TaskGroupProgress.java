package no.ntnu.fullstack.queues.task;

import java.util.List;

public class TaskGroupProgress {
    private int number;
    private int required;
    private int completed;
    private List<TaskProgress> taskProgress;

    public TaskGroupProgress() {
    }

    public TaskGroupProgress(int number, int required) {
        this.number = number;
        this.required = required;
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

