package no.ntnu.fullstack.queues.task;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("Couldn't find course " + id);
    }

}
