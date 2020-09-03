package ua.ihor.blablatracker.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.ihor.blablatracker.entity.Task;
import ua.ihor.blablatracker.exception.TaskNotFoundException;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    default Task getById(Long id) {
        return findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }
}
