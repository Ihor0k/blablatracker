package ua.ihor.blablatracker.service;

import org.springframework.data.jpa.domain.Specification;
import ua.ihor.blablatracker.TaskSpecification;
import ua.ihor.blablatracker.entity.Task;
import ua.ihor.blablatracker.dto.response.TasksAssignedToUser;
import ua.ihor.blablatracker.dto.response.TasksByDepartment;

import java.util.List;

public interface ReportService {
    List<TasksByDepartment> tasksByDepartment(Specification<Task> taskSpecification);

    List<TasksAssignedToUser> tasksAssignedToUser(TaskSpecification taskSpecification);
}
