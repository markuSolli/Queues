package no.ntnu.fullstack.queues.location;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Deprecated
@Service
public class CampusService {
    private final CampusRepository campusRepository;

    public CampusService(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    /**
     * Add a new Campus to the database
     * @param campus the new campus
     * @return the saved campus
     */
    public Campus addCampus(Campus campus) {
        return campusRepository.save(campus);
    }

    /**
     * Delete a Campus from the database
     * @param campus the campus to delete
     * @throws NoSuchElementException
     */
    public void deleteCampus(Campus campus) throws NoSuchElementException {
        if(!campusRepository.existsById(campus.getName())) throw new NoSuchElementException();
        campusRepository.delete(campus);
    }
}
