package ua.ihor.blablatracker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.ihor.blablatracker.entity.Attachment;
import ua.ihor.blablatracker.exception.AttachmentNotFoundException;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, String> {

    default Attachment getById(String id) {
        return findById(id).orElseThrow(() -> new AttachmentNotFoundException(id));
    }
}
