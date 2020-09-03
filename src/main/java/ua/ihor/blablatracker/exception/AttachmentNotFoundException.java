package ua.ihor.blablatracker.exception;

public class AttachmentNotFoundException extends EntityNotFoundException {
    public AttachmentNotFoundException(String id) {
        super("Attachment", id);
    }
}
