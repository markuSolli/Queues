package no.ntnu.fullstack.queues.location;

import javax.persistence.*;

@Entity
@IdClass(BuildingID.class)
public class Building {

    @Id
    private String name;
    @Id
    private String campus;

    protected Building() {}

    public Building(String name, String campus) {
        this.name = name;
        this.campus = campus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }
}
