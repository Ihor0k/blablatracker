package ua.ihor.blablatracker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.ihor.blablatracker.entity.User;
import ua.ihor.blablatracker.exception.UserNotFoundException;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    default User getById(Long id) {
        return findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
