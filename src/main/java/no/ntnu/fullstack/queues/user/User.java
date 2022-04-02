package no.ntnu.fullstack.queues.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.ntnu.fullstack.queues.course.UserCourse;
import no.ntnu.fullstack.queues.task.Approved;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
public class User implements UserDetails {

    @Id
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private boolean enabled = true;
    @JsonIgnore
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<UserCourse> courses = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Approved> approved = new HashSet<>();

    protected User() {}

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<UserCourse> getCourses() {
        return courses;
    }

    public void setCourses(Set<UserCourse> courses) {
        this.courses = courses;
    }

    public Set<Approved> getApproved() {
        return approved;
    }

    public void setApproved(Set<Approved> approved) {
        this.approved = approved;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toString()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
