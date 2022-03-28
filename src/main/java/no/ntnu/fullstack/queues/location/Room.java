package no.ntnu.fullstack.queues.location;

import no.ntnu.fullstack.queues.course.Course;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private File map;

    @ManyToOne
    private Building building;

    @ManyToMany
    private List<Course> courses;

    protected Room() {}

    public Room(String name, Building building) {
        this.name = name;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getMap() {
        return map;
    }

    public void setMap(File map) {
        this.map = map;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
