package no.ntnu.fullstack.queues.authentication;

import io.jsonwebtoken.JwtException;
import no.ntnu.fullstack.queues.authentication.jwt.JwtResponse;
import no.ntnu.fullstack.queues.authentication.jwt.JwtUtil;
import no.ntnu.fullstack.queues.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthenticationController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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
     * Used for getting information about one self, such that this can be displayed where needed
     * @param authentication
     * @return
     */
    @GetMapping("/me")
    public UserInfo whoAmI(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return new UserInfo(user.getUsername(), user.getFirstName(), user.getLastName());
    }



}