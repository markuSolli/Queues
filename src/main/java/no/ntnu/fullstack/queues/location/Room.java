package no.ntnu.fullstack.queues.location;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.File;

@Entity
@IdClass(RoomID.class)
public class Room {

    @Id
    private String name;
    @Id
    private BuildingID building;
    private File map;

    protected Room() {}

    public Room(String name, BuildingID building) {
        this.name = name;
        this.building = building;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BuildingID getBuilding() {
        return building;
    }

    public void setBuilding(BuildingID building) {
        this.building = building;
    }

    public File getMap() {
        return map;
    }

    public void setMap(File map) {
        this.map = map;
    }
}
