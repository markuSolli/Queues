package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.task.TaskGroupProgress;

import java.util.List;

/**
 * DTO for sending information about the courses a student is currently enrolled in
 */
public class CourseProgress {

    private Long id;
    private String code;
    private String title;
    private boolean active;
    private boolean archived;
    private Season season;
    private int year;
    private List<TaskGroupProgress> taskGroupProgress;
    public CourseProgress(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<TaskGroupProgress> getTaskGroupProgress() {
        return taskGroupProgress;
    }

    public void setTaskGroupProgress(List<TaskGroupProgress> taskGroupProgress) {
        this.taskGroupProgress = taskGroupProgress;
    }
}
