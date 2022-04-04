package no.ntnu.fullstack.queues.user;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

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
            user.setActivation(generateActivationCode());
            sendActivationMail(user);
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

    /**
     * Send an e-mail from the applications account to the given users e-mail account with its
     * activation link.
     * @param user the user to send mail to
     */
    private void sendActivationMail(User user){
        String serverEmail = "queuesfullstack@gmail.com";
        String serverPassword = "d4741nG3N1or";
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(serverEmail, serverPassword);
                }
            });

            String link = "http://localhost:8080/activateUser/" + user.getActivation();

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(serverEmail, false));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            msg.setSubject("Activate your Queues profile");
            msg.setSentDate(new Date());
            msg.setContent("<p>Hello " + user.getFirstName() + " " + user.getLastName() + "!" + "</p>" +
                    "<p>An account has been made for you, visit the following link to set a password:</p>" +
                    "<a href=" + link + ">" + link + "</a>", "text/html");

            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate a random alphanumerical String of 10 characters
     * @return a random string.
     */
    private String generateActivationCode(){
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Get the user object with the given activation code
     *
     * @param activation the activation code generated for the user.
     * @return the matching user object.
     * @throws ActivationCodeNotFoundException
     */
    public User getUserByActivationCode(String activation) throws ActivationCodeNotFoundException{
        return userRepository.findByActivation(activation).orElseThrow(() -> new ActivationCodeNotFoundException(activation));
    }

    /**
     * Activate an existing user, assigning a password
     *
     * @param userDTO user object with activation code instead of email
     * @return the new User object
     * @throws ActivationCodeNotFoundException
     */
    public User activateUser(UserDTO userDTO) throws ActivationCodeNotFoundException {
        User existingUser = userRepository.findByActivation(userDTO.getEmail()).orElseThrow(() -> new ActivationCodeNotFoundException(userDTO.getEmail()));

        String encodedPassword = bCrypt.encode(userDTO.getPassword());
        User user = new User(existingUser.getEmail(), encodedPassword, existingUser.getFirstName(), existingUser.getLastName());
        user.setRole(existingUser.getRole());

        return userRepository.save(user);
    }

    /**
     * Get all users in the database
     * @return an iterable of all users
     */
    public Iterable<UserInfo> getAllUsers(){
        Iterable<User> users = userRepository.findAll();
        List<UserInfo> userInfos = new ArrayList<>();
        for(User u : users){
            userInfos.add(new UserInfo(u.getEmail(), u.getFirstName(), u.getLastName(), u.getRole()));
        }
        return userInfos;
    }

    /**
     * Register a new, unactivated user. Works as long as the role of the new user
     * is less than or equal to the caller.
     * @param user the new user
     * @param caller the user calling this method
     * @return the created user
     * @throws UserAlreadyExistsException
     * @throws IllegalAccessError
     */
    public User register(User user, User caller) throws UserAlreadyExistsException{
        if(userExits(user.getEmail())) throw new UserAlreadyExistsException("User already exists");
        if(caller.getRole().ordinal() > user.getRole().ordinal()) throw new IllegalAccessError();
        User newUser = new User(user.getEmail(), null, user.getFirstName(), user.getLastName());
        newUser.setRole(user.getRole());
        newUser.setActivation(generateActivationCode());
        sendActivationMail(newUser);
        return userRepository.save(newUser);
    }
}
