package no.ntnu.fullstack.queues.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.fullstack.queues.authentication.jwt.JwtResponse;
import no.ntnu.fullstack.queues.authentication.jwt.JwtUtil;
import no.ntnu.fullstack.queues.course.CourseController;
import no.ntnu.fullstack.queues.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * When a user tries to login, this method will be called. It will try to login a with the given credentials,
     * and if the credentials are valid, the #successfullAuthentication method is called
     *
     * @param request http request
     * @param response http response
     * @return Authentication
     * @throws AuthenticationException if the login there is an error with the login attemt or the given credentials
     *          were invalid
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            logger.info("Attempting authentication...");
            // Reading the user data from the request body
            UserDTO authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);

            // Trying to authenticate the user
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            );
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            logger.error("Authentication failed... Invalid format");
            throw new CustomAuthenticationException("Invalid format");
        } catch (AuthenticationException e) {
            logger.error("Authentication failed... Invalid credentials");
            throw new CustomAuthenticationException("Invalid credentials");
        }
    }

    /**
     * If an authentication attempts is successful, this method is called. In other words, we have a user, and we
     * must now generate them an access token they can use to make authentication requests.
     *
     * @param request http request
     * @param response http response
     * @param chain filter chain
     * @param authResult result of authentication
     * @throws IOException if
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info("Authentication successful");
        // Creating access token and add it to the authorization header
        String accessToken = jwtUtil.createAccessToken(authResult.getName());

        // We also have to make a refresh token for refreshing access tokens
        String refreshToken = jwtUtil.createRefreshToken(authResult.getName());

        Cookie cookie = new Cookie(jwtUtil.cookieName(), refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // Sending the token as a response to the login
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new JwtResponse(accessToken)));
    }
}
