package no.ntnu.fullstack.queues.location;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Deprecated
@Service
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    /**
     * Add a new Building to the database
     * @param building the new Building
     * @return the saved building
     */
    public Building addBuilding(Building building) {
        return buildingRepository.save(building);
    }

    /**
     * Delete a building from the database
     * @param building the building to delete
     * @throws NoSuchElementException
     */
    public void deleteBuilding(Building building) throws NoSuchElementException{
        if(!buildingRepository.existsById(building.getId())) throw new NoSuchElementException();
        buildingRepository.delete(building);
    }
}
