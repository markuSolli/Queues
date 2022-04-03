package no.ntnu.fullstack.queues.location;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.NoSuchElementException;

@Deprecated
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Add a new room to database
     * @param room the new room
     * @return the created room
     */
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    /**
     * Delete a room from database
     * @param room the room to delete
     * @throws NoSuchElementException
     */
    public void deleteRoom(Room room) throws NoSuchElementException{
        if(!roomRepository.existsById(room.getId())) throw new NoSuchElementException();
        roomRepository.delete(room);
    }

    /**
     * Add a map to a room
     * @param room the room that gets the map
     * @param map the map to upload
     * @return the new room object
     * @throws NoSuchElementException
     */
    public Room addMap(Room room, File map) throws NoSuchElementException {
        Room existingRoom = roomRepository.findById(room.getId()).orElseThrow();
        existingRoom.setMap(map);
        return roomRepository.save(existingRoom);
    }

    /**
     * Get all rooms in the database
     * @return an Iterable of all Rooms
     */
    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
