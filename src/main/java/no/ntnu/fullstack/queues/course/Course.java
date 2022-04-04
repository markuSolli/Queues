package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.location.Location;
import no.ntnu.fullstack.queues.task.Task;
import no.ntnu.fullstack.queues.task.TaskGroup;
import no.ntnu.fullstack.queues.user.User;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String code;
    private String title;
    private Season season;
    private int year;
    private boolean archived = false;
    private boolean active = false;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Location> rooms = new HashSet<>();
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TaskGroup> taskGroups = new HashSet<>();
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCourse> users = new HashSet<>();

    protected Course(){}

    public Course(String code, String title, Season season, int year) {
        this.code = code;
        this.title = title;
        this.season = season;
        this.year = year;
    }

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

    public Set<TaskGroup> getTaskGroups() {
        return taskGroups;
    }

    public void setTaskGroups(Set<TaskGroup> taskGroups) {
        this.taskGroups.clear();
        if(taskGroups == null) {
            return;
        }
        for(TaskGroup taskGroup : taskGroups) {
            taskGroup.setCourse(this);
            for(Task task : taskGroup.getTasks()) {
                task.setTaskGroup(taskGroup);
            }
        }
        this.taskGroups.addAll(taskGroups);
    }

    public Set<Location> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Location> rooms) {
        if(rooms == null) {
            return;
        }
        this.rooms.clear();
        this.rooms.addAll(rooms);
    }

    public Set<UserCourse> getUsers() {
        return users;
    }

    public void setUsers(Set<UserCourse> users) {
        this.users = users;
    }

    /**
     * Adds a user to this course and defines which role this user has in the course
     *
     * @param user user to add to course
     * @param courseRole role of user in the course
     */
    public void addUser(User user, CourseRole courseRole) {
        UserCourse userCourse = new UserCourse(user, this, courseRole);
        users.add(userCourse);
    }

    /**
     * Removes a user from the course
     *
     * @param user user to remove from course
     */
    public void removeUser(User user) {
        UserCourse userCourseToRemove = null;
        for(UserCourse userCourse : users) {
            if(userCourse.getUser().equals(user)) {
                userCourseToRemove = userCourse;
                break;
            }
        }
        if(userCourseToRemove != null) {
            users.remove(userCourseToRemove);
        }
    }

    /**
     * Find which course role a user has in this course. If the user is not in the course, the method returns null
     *
     * @param user user to look for
     * @return course role of user, null if the user is not in the course
     */
    public CourseRole findCourseRole(User user) {
        for(UserCourse userCourse : users) {
            if(userCourse.getUser().equals(user)) {
                return userCourse.getRole();
            }
        }
        return null;
    }

    /**
     * Checks if a user is in a course. Returns true if so, otherwise it returns false
     *
     * @param user user to look for
     * @return true of user is in the course, false if not
     */
    public boolean isUserInCourse(User user) {
        return findCourseRole(user) != null;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", season=" + season+
                ", year=" + year+
                ", archived=" + archived +
                ", active=" + active +
                ", tasks=" + taskGroups +
                ", rooms=" + rooms +
                ", users=" + users+
                '}';
    }
}
