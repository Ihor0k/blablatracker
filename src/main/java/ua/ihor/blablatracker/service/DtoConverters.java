package ua.ihor.blablatracker.service;

import ua.ihor.blablatracker.dto.response.*;
import ua.ihor.blablatracker.entity.*;
import ua.ihor.blablatracker.exception.BadRequestException;

public interface DtoConverters {

    TaskDto taskToDto(Task task);

    TaskDetailsDto taskToDetailsDto(Task task);

    UserDto userToDto(User user);

    DepartmentDto departmentToDto(Department department);

    CommentDto commentToDto(Comment comment);

    AttachmentDto attachmentToDto(Attachment attachment);

    static String statusToString(Task.Status status) {
        String result = null;
        switch (status) {
            case TODO:
                result = "todo";
                break;
            case IN_PROGRESS:
                result = "in_progress";
                break;
            case DONE:
                result = "done";
                break;
        }
        return result;
    }

    static Task.Status stringToStatus(String status) {
        Task.Status result;
        switch (status.toLowerCase()) {
            case "todo":
                result = Task.Status.TODO;
                break;
            case "in_progress":
                result = Task.Status.IN_PROGRESS;
                break;
            case "done":
                result = Task.Status.DONE;
                break;
            default:
                throw new BadRequestException("Unknown status: " + status);
        }
        return result;
    }
}
