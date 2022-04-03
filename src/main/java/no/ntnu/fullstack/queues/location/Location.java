package no.ntnu.fullstack.queues.location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long locationId;
    private int buildingId;
    private String buildingName;
    private int campusId;
    private int floorId;
    private String floorName;
    private int id;
    private String identifier;
    private int poiId;
    private String title;
    private int zLevel;
    private float lat;
    private float lon;

    protected Location() {}

    public Location(int buildingId, String buildingName, int campusId, int floorId, String floorName, int id, String identifier, int poiId, String title, int zLevel, float lat, float lon) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.campusId = campusId;
        this.floorId = floorId;
        this.floorName = floorName;
        this.id = id;
        this.identifier = identifier;
        this.poiId = poiId;
        this.title = title;
        this.zLevel = zLevel;
        this.lat = lat;
        this.lon = lon;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getCampusId() {
        return campusId;
    }

    public void setCampusId(int campusId) {
        this.campusId = campusId;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getPoiId() {
        return poiId;
    }

    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getzLevel() {
        return zLevel;
    }

    public void setzLevel(int zLevel) {
        this.zLevel = zLevel;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
