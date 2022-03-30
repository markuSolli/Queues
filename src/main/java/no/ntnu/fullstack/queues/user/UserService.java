package no.ntnu.fullstack.queues.user;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCrypt;

    public UserService(UserRepository userRepository, PasswordEncoder bCrypt) {
        this.userRepository = userRepository;
        this.bCrypt = bCrypt;
    }

    /**
     * Takes a usernames and tries to find a user with that username in the database
     * @param username username (email) to look for
     * @return user, if there is one
     * @throws UsernameNotFoundException if there is no user with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Couldn't find " + username));
    }

    /**
     * Creates a new user in the database, if and only if the user does not already exist in the database and
     * after their password has been encrypted.
     *
     * @param userDTO information about the user to signup
     * @return user that was signed up
     */
    public User signup(UserDTO userDTO, Role role) throws UserAlreadyExistsException {
        if (userExits(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        if(userDTO.getPassword() == null) {
            User user = new User(userDTO.getEmail(), null, userDTO.getFirstName(), userDTO.getLastName());
            user.setRole(role);
            user.setEnabled(false);
            return userRepository.save(user);
        }

        // Encoding password
        String encodedPassword = bCrypt.encode(userDTO.getPassword());
        // Creating user-object
        User user = new User(userDTO.getEmail(), encodedPassword, userDTO.getFirstName(), userDTO.getLastName());
        user.setRole(role);

        return userRepository.save(user);
    }

    /**
     * Check if the user already exists in the database
     *
     * @param username username to check (email in this case)
     * @return true if user exists, false if not
     */
    private boolean userExits(String username) {
        return userRepository.findByEmail(username).isPresent();
    }


    /**
     * This is used for defining the hierarchy of the roles the users can have.
     *
     * @return the hierarchy structure of the roles in this program
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy =
                "ROLE_ADMIN > ROLE_TEACHER \n " +
                "ROLE_TEACHER > ROLE_ASSISTANT \n " +
                "ROLE_ASSISTANT > ROLE_STUDENT";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    public boolean userExists(String email) {
        return userRepository.existsById(email);
    }
}
