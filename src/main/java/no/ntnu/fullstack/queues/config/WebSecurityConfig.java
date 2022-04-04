package no.ntnu.fullstack.queues.config;

import no.ntnu.fullstack.queues.authentication.CustomUsernamePasswordAuthenticationFilter;
import no.ntnu.fullstack.queues.authentication.jwt.JwtUtil;
import no.ntnu.fullstack.queues.authentication.jwt.JwtValidationFilter;
import no.ntnu.fullstack.queues.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder bCrypt;
    private final JwtUtil jwtUtil;

    public WebSecurityConfig(UserService userService, PasswordEncoder bCrypt, JwtUtil jwtUtil) {
        this.userService = userService;
        this.bCrypt = bCrypt;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilter(new CustomUsernamePasswordAuthenticationFilter(authenticationManager(), jwtUtil))
                    .addFilterAfter(new JwtValidationFilter(jwtUtil, userService), CustomUsernamePasswordAuthenticationFilter.class)
                .logout()
                    .addLogoutHandler(new CookieClearingLogoutHandler(jwtUtil.cookieName()))
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .and()
                .authorizeRequests()
                    // Authentication endpoints
                    .antMatchers("/login").permitAll()
                    .antMatchers("/signup").permitAll()
                    .antMatchers("/token").permitAll()
                    .antMatchers("/example").permitAll()
                    .antMatchers("/activate").permitAll()
                    .antMatchers("/activation/**").permitAll()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .antMatchers("/v3/api-docs/**").permitAll()
                    .antMatchers( "/register").hasRole("TEACHER")
                    .antMatchers( "/users").hasRole("ADMIN")
                    // Queue endpoints
                    .antMatchers("/queue/*/assist").hasRole("ASSISTANT")
                    .antMatchers("/queue/*/approve").hasRole("ASSISTANT")
                    // Course endpoints
                    .antMatchers(HttpMethod.POST,"/courses/**").hasRole("TEACHER")
                    .antMatchers(HttpMethod.PUT,"/courses/**").hasRole("TEACHER")
                    .antMatchers(HttpMethod.DELETE,"/courses/**").hasRole("TEACHER")
                    .antMatchers(HttpMethod.PATCH,"/courses/active").hasRole("ASSISTANT")
                    .antMatchers(HttpMethod.PATCH,"/courses/archive").hasRole("TEACHER")
                .anyRequest().authenticated();
    }


    // Setup
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCrypt);
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
