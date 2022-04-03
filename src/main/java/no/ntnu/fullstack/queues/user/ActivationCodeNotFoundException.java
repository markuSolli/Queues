package no.ntnu.fullstack.queues.user;

public class ActivationCodeNotFoundException extends RuntimeException{

    public ActivationCodeNotFoundException(String activation) {
        super("Couldn't find user with activation code " + activation);
    }
}
