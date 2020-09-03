package ua.ihor.blablatracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDetailsDto {
    private Long id;
    private String name;
    private String description;
    private UserDto createdBy;
    private Date createdAt;
    private UserDto assignedTo;
    private String status;
    private List<CommentDto> comments;
    private List<AttachmentDto> attachments;
}
