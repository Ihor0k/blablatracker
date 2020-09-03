package ua.ihor.blablatracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ihor.blablatracker.dto.request.CreateTaskDto;
import ua.ihor.blablatracker.dto.request.UpdateTaskDto;
import ua.ihor.blablatracker.dto.response.TaskDetailsDto;
import ua.ihor.blablatracker.dto.response.TaskDto;
import ua.ihor.blablatracker.entity.Task;
import ua.ihor.blablatracker.entity.User;
import ua.ihor.blablatracker.repository.TaskRepository;
import ua.ihor.blablatracker.repository.UserRepository;

import java.util.Date;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private DtoConverters dtoConverters;

    @Override
    public Page<TaskDto> getTasks(Specification<Task> specification, Pageable pageable) {
        return taskRepository
                .findAll(specification, pageable)
                .map(dtoConverters::taskToDto);
    }

    @Override
    public TaskDetailsDto getTaskDetails(long taskId) {
        Task task = taskRepository.getById(taskId);
        return dtoConverters.taskToDetailsDto(task);
    }

    @Override
    public TaskDto createTask(CreateTaskDto createTaskDto) {
        Long createdByUserId = createTaskDto.getCreatedByUserId();
        User createdByUser = createdByUserId != null ? userRepository.getById(createdByUserId) : null;
        Long assignedToUserId = createTaskDto.getAssignedToUserId();
        User assignedToUser = assignedToUserId != null ? userRepository.getById(assignedToUserId) : null;
        Task task = new Task(
                null,
                createTaskDto.getName(),
                createTaskDto.getDescription(),
                createdByUser,
                new Date(),
                assignedToUser,
                DtoConverters.stringToStatus(createTaskDto.getStatus()),
                null,
                null
        );
        Task createdTask = taskRepository.save(task);
        return dtoConverters.taskToDto(createdTask);
    }

    @Override
    public TaskDto updateTask(long taskId, UpdateTaskDto updateTaskDto) {
        Task task = taskRepository.getById(taskId);

        String name = updateTaskDto.getName();
        if (name != null) task.setName(name);

        String description = updateTaskDto.getDescription();
        if (description != null) task.setDescription(description);

        Long assignedToUserId = updateTaskDto.getAssignedToUserId();
        if (assignedToUserId != null) {
            User assignedToUser = userRepository.getById(assignedToUserId);
            task.setAssignedTo(assignedToUser);
        }

        String status = updateTaskDto.getStatus();
        if (status != null) task.setStatus(DtoConverters.stringToStatus(status));

        Task updatedTask = taskRepository.save(task);
        return dtoConverters.taskToDto(updatedTask);
    }

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setDtoConverters(DtoConverters dtoConverters) {
        this.dtoConverters = dtoConverters;
    }
}
