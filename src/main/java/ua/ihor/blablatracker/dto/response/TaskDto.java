package ua.ihor.blablatracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private UserDto createdBy;
    private Date createdAt;
    private UserDto assignedTo;
    private String status;
}
