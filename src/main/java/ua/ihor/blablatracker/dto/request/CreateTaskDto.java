package ua.ihor.blablatracker.dto.request;

import lombok.Data;

@Data
public class CreateTaskDto {
    private String name;
    private String description;
    private Long createdByUserId;
    private Long assignedToUserId;
    private String status;
}
