package ua.ihor.blablatracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ihor.blablatracker.dto.response.*;
import ua.ihor.blablatracker.entity.*;
import ua.ihor.blablatracker.service.external.UserRatingService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class DtoConvertersImpl implements DtoConverters {
    private UserRatingService userRatingService;

    @Override
    public TaskDto taskToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                userToDto(task.getCreatedBy()),
                task.getCreatedAt(),
                userToDto(task.getAssignedTo()),
                DtoConverters.statusToString(task.getStatus())
        );
    }

    @Override
    public TaskDetailsDto taskToDetailsDto(Task task) {
        return new TaskDetailsDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                userToDto(task.getCreatedBy()),
                task.getCreatedAt(),
                userToDto(task.getAssignedTo()),
                DtoConverters.statusToString(task.getStatus()),
                task.getComments().stream().map(this::commentToDto).collect(Collectors.toList()),
                task.getAttachments().stream().map(this::attachmentToDto).collect(Collectors.toList())
        );
    }

    @Override
    public UserDto userToDto(User user) {
        Long id = user.getId();
        return new UserDto(
                id,
                user.getName(),
                departmentToDto(user.getDepartment()),
                userRatingService.getUserRating(id)
        );
    }

    @Override
    public DepartmentDto departmentToDto(Department department) {
        return new DepartmentDto(
                department.getId(),
                department.getName()
        );
    }

    @Override
    public CommentDto commentToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                userToDto(comment.getCreatedBy()),
                comment.getCreatedAt()
        );
    }

    @Override
    public AttachmentDto attachmentToDto(Attachment attachment) {
        String name = attachment.getName();
        String nameEncoded = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String url = String.format("/api/tasks/%d/attachments/%s/%s", attachment.getTask().getId(), attachment.getId(), nameEncoded);
        return new AttachmentDto(
                name,
                attachment.getMediaType(),
                attachment.getSize(),
                url
        );
    }

    @Autowired
    public void setUserRatingService(UserRatingService userRatingService) {
        this.userRatingService = userRatingService;
    }
}
