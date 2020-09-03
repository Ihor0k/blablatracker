package ua.ihor.blablatracker.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.ihor.blablatracker.entity.Task;

@Data
@AllArgsConstructor
public class UserStatusTaskNumView {
    private Long userId;
    private String userName;
    private Task.Status status;
    private Long taskNum;
}
