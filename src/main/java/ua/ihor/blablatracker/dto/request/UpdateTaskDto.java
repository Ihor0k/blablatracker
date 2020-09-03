package ua.ihor.blablatracker.dto.request;

import lombok.Data;

@Data
public class UpdateTaskDto {
    private String name;
    private String description;
    private Long assignedToUserId;
    private String status;
}
