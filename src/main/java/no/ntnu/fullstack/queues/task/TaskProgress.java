package no.ntnu.fullstack.queues.task;

/**
 * DTO that can be attached to indicate progress at a task
 */
public class TaskProgress {
    private int number;
    private boolean approved;

    public TaskProgress(int task, boolean approved) {
        this.number = task;
        this.approved = approved;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}

