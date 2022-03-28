package no.ntnu.fullstack.queues.location;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Campus {

    @Id
    private String name;

    protected Campus() {}

    public Campus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
