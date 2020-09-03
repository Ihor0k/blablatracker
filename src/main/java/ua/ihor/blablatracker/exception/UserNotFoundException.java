package ua.ihor.blablatracker.exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(long id) {
        super("User", id);
    }
}
