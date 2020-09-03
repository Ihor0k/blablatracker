package ua.ihor.blablatracker.exception;

public class CommentNotFoundException extends EntityNotFoundException {
    public CommentNotFoundException(long id) {
        super("Comment", id);
    }
}
