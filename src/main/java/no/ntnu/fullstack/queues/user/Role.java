package no.ntnu.fullstack.queues.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Used for managing the different roles in the application
 */
public enum Role {
    ADMIN,
    TEACHER,
    STUDENT
}
