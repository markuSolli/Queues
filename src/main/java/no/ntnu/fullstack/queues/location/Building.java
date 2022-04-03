package no.ntnu.fullstack.queues.location;

import javax.persistence.*;

@Deprecated
@Entity
public class Building {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne
    private Campus campus;

    protected Building() {}

    public Building(String name, Campus campus) {
        this.name = name;
        this.campus = campus;
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

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }
}
