package no.ntnu.fullstack.queues.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public User signup(UserDTO userDTO) throws UserAlreadyExistsException {
        if (userExits(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        System.out.println(userDTO.getEmail());
        System.out.println(userDTO.getPassword());

        // Encoding password
        String encodedPassword = bCrypt.encode(userDTO.getPassword());

        // Creating user-object
        User user = new User(userDTO.getEmail(), encodedPassword, userDTO.getFirstName(), userDTO.getLastName());

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
}
