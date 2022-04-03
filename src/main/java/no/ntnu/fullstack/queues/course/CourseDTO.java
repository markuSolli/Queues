package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.location.Location;
import no.ntnu.fullstack.queues.task.TaskGroup;
import no.ntnu.fullstack.queues.user.User;

import java.util.List;
import java.util.Set;

public class CourseDTO {
    private String code;
    private String title;
    private Season season;
    private int year;
    private boolean archived;
    private boolean active;
    private List<User> students;
    private List<User> assistants;
    private List<User> teachers;
    private Set<TaskGroup> taskGroups;
    private Set<Location> rooms;

    public CourseDTO() {
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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<User> getAssistants() {
        return assistants;
    }

    public void setAssistants(List<User> assistants) {
        this.assistants = assistants;
    }

    public List<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<User> teachers) {
        this.teachers = teachers;
    }

    public Set<TaskGroup> getTaskGroups() {
        return taskGroups;
    }

    public void setTaskGroups(Set<TaskGroup> taskGroups) {
        this.taskGroups = taskGroups;
    }

    public Set<Location> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Location> rooms) {
        this.rooms = rooms;
    }
}

