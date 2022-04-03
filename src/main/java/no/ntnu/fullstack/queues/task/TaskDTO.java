package no.ntnu.fullstack.queues.task;

/**
 * Used to transfer data about a task
 */
public class TaskDTO {
    private int number;
    private boolean completed;

    public TaskDTO() {}

    public TaskDTO(int number, boolean completed) {
        this.number = number;
        this.completed = completed;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
