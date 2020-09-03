package ua.ihor.blablatracker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.ihor.blablatracker.entity.Comment;
import ua.ihor.blablatracker.exception.CommentNotFoundException;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    default Comment getById(Long id) {
        return findById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }
}
