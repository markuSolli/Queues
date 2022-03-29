package no.ntnu.fullstack.queues.config;

import no.ntnu.fullstack.queues.user.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserService userService;
    private final PasswordEncoder bCrypt;

    public WebSecurityConfig(UserService userService, PasswordEncoder bCrypt) {
        this.userService = userService;
        this.bCrypt = bCrypt;
    }

}
