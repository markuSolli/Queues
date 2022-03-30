package no.ntnu.fullstack.queues.course;

import no.ntnu.fullstack.queues.location.Room;
import no.ntnu.fullstack.queues.user.User;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String code;
    private String title;
    private Date startDate;
    private Date endDate;
    private boolean archived = false;
    private boolean active = false;
    @ManyToMany(targetEntity = Room.class)
    private List<Room> rooms;
    @OneToMany
    private List<UserCourse> users = new ArrayList<>();

    protected Course(){}

    public Course(String code, String title, Date startDate, Date endDate) {
        this.code = code;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<UserCourse> getUsers() {
        return users;
    }

    public void setUsers(List<UserCourse> users) {
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
        user.getCourses().add(userCourse);
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
            userCourseToRemove.getUser().getCourses().remove(userCourseToRemove);
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", archived=" + archived +
                ", active=" + active +
                ", rooms=" + rooms +
                '}';
    }
}
