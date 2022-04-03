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
import java.net.InetAddress;
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

    public boolean userExists(String email) {
        return userRepository.existsById(email);
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

            String link = "http://localhost:8080/activation/" + user.getActivation();

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
     * @param activation the activation code generated for the user.
     * @return the matching user object.
     * @throws NoSuchElementException
     */
    public User getUserByActivationCode(String activation) throws NoSuchElementException{
        Optional<User> user = userRepository.findByActivation(activation);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new NoSuchElementException();
        }
    }
}
