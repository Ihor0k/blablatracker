package ua.ihor.blablatracker.exception;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(long id) {
        super("Task", id);
    }
}
