package no.ntnu.fullstack.queues.task;

/**
 * DTO that can be attached to indicate progress at a task
 */
public class TaskProgress {
    private Long id;
    private int number;
    private boolean approved;

    public TaskProgress(Long id, int task, boolean approved) {
        this.id = id;
        this.number = task;
        this.approved = approved;
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

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}

