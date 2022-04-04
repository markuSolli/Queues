package no.ntnu.fullstack.queues.authentication;

import io.jsonwebtoken.JwtException;
import no.ntnu.fullstack.queues.authentication.jwt.JwtResponse;
import no.ntnu.fullstack.queues.authentication.jwt.JwtUtil;
import no.ntnu.fullstack.queues.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthenticationController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user, Authentication authentication){
        try{
            User caller = (User) authentication.getPrincipal();
            User newUser = userService.register(user, caller);
            return new ResponseEntity<>(newUser.getEmail(), HttpStatus.CREATED);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }
    }

    /**
     * Signs up a user when a post body with a request body with and email and password is sent.
     *
     * @param userDTO email and password for the user
     * @return The email of the user if the signup is successful, an error message if it wasn't
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.signup(userDTO, Role.STUDENT);
            return new ResponseEntity<>(user.getUsername(), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Used for refreshing access tokens. The refresh token cookie is checked, and if it contains a valid refresh token
     * a new access token is created for the subject in the refresh token and the token is sent back. The refresh
     * token is also refreshed, effectively extending it's expiration date for further usage
     *
     * @param request http request
     * @param response http response
     * @return access token if refresh token is valid, blank if not
     */
    @PostMapping("/token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = "";
        if (request.getCookies() == null) {
            // If there is no cookie, the user is not trying to fetch a new accessToken anyway
            // TODO: Assess if this is needed
            logger.error("No refresh token");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for (Cookie c : request.getCookies()) {
            if (c.getName().equals(jwtUtil.cookieName())) {
                refreshToken = c.getValue();
            }
        }

        if (refreshToken.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // If we get here, we should have a refresh token. Now we must validate it
        try {
            String subject = jwtUtil.decode(refreshToken);

            // If we get here, the token has been validated
            logger.info("Refresh token is valid, sending new access token...");

            // We create a new access token for the user
            String accessToken = jwtUtil.createAccessToken(subject);

            // We also create a new refresh token in a http-only cookie so the user can fetch new access tokens

            // when needed
            Cookie refreshTokenCookie = new Cookie(jwtUtil.cookieName(), jwtUtil.createRefreshToken(subject));
            refreshTokenCookie.setHttpOnly(true);

            response.addCookie(refreshTokenCookie);
            return new ResponseEntity<>(new JwtResponse(accessToken), HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Returns the currently logged on user and some information about them
     *
     * @return information about the currently loggen in user
     */
    @GetMapping("/me")
    public ResponseEntity<UserInfo> fetchCurrentUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(new UserInfo(user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole()), HttpStatus.OK);
    }

    /**
     * This method is used for populating the database with some example users for testing
     */
    @GetMapping("/example")
    public void populateWithExampleData() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("roger@roger.no");
        userDTO.setPassword("roger");
        userDTO.setFirstName("Roger");
        userDTO.setLastName("Rogersen");
        try {
            userService.signup(userDTO, Role.ADMIN);
        } catch (Exception e) {
            // Already signed up!
        }

        UserDTO teacher = new UserDTO("teacher@teacher.no", "Lærer", "Vekkesen");
        teacher.setPassword("lærer");

        UserDTO assistant = new UserDTO("assistant@assistant.no", "Assistent", "Hjelpersen");
        assistant.setPassword("assistent");

        UserDTO student = new UserDTO("student@student.no", "Student", "Øvesen");
        student.setPassword("student");
        try {
            userService.signup(teacher, Role.TEACHER);
            userService.signup(assistant, Role.ASSISTANT);
            userService.signup(student, Role.STUDENT);
        } catch (Exception e) {
            // Already signed up!
        }
    }

    @GetMapping("/activation/{code}")
    public ResponseEntity<UserInfo> getUserByActivationCode(@PathVariable String code){
        logger.info("Sending user info for activation code " + code + "...");
        try {
            User user = userService.getUserByActivationCode(code);
            return new ResponseEntity<>(new UserInfo(user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole()), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/activate")
    public ResponseEntity<UserInfo> activateUser(@RequestBody UserDTO userDTO){
        logger.info("Activating user {} from code...", userDTO.getEmail());
        try {
            User user = userService.activateUser(userDTO);
            return new ResponseEntity<>(new UserInfo(user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole()), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<UserInfo>> getAllUsers(){
        logger.info("Fetching all users...");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}