package no.ntnu.fullstack.queues.location;

import java.io.Serializable;
import java.util.Objects;

public class RoomID implements Serializable {
    private String name;
    private BuildingID building;

    public RoomID(String name, BuildingID building) {
        this.name = name;
        this.building = building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomID roomID = (RoomID) o;
        return name.equals(roomID.name) && building.equals(roomID.building);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, building);
    }
}
