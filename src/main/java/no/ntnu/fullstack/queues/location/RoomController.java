package no.ntnu.fullstack.queues.location;

import no.ntnu.fullstack.queues.course.CourseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.NoSuchElementException;

@Deprecated
@RestController
@CrossOrigin
@RequestMapping("/room")
public class RoomController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Room>> getAllRooms(){
        logger.info("Retrieving all rooms...");
        return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody Room room){
        logger.info("Adding room " + room.toString() + "...");
        return new ResponseEntity<>(roomService.addRoom(room), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Room> addMap(@RequestBody Room room, File map){
        logger.info("Adding map to room " + room.toString() + "...");
        try {
            Room newRoom = roomService.addMap(room, map);
            return new ResponseEntity<>(newRoom, HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteRoom(@RequestBody Room room){
        logger.info("Deleting room " + room.toString() + "...");
        try {
            roomService.deleteRoom(room);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
