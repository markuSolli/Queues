package no.ntnu.fullstack.queues.location;

import java.io.Serializable;
import java.util.Objects;

public class BuildingID implements Serializable {
    private String name;
    private String campus;

    public BuildingID(String name, String campus) {
        this.name = name;
        this.campus = campus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingID that = (BuildingID) o;
        return name.equals(that.name) && campus.equals(that.campus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, campus);
    }
}
