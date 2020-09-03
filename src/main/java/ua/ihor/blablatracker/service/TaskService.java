package ua.ihor.blablatracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ua.ihor.blablatracker.dto.request.CreateTaskDto;
import ua.ihor.blablatracker.dto.request.UpdateTaskDto;
import ua.ihor.blablatracker.dto.response.TaskDetailsDto;
import ua.ihor.blablatracker.dto.response.TaskDto;
import ua.ihor.blablatracker.entity.Task;

public interface TaskService {

    Page<TaskDto> getTasks(Specification<Task> specification, Pageable pageable);

    TaskDetailsDto getTaskDetails(long taskId);

    TaskDto createTask(CreateTaskDto createTaskDto);

    TaskDto updateTask(long taskId, UpdateTaskDto task);
}
