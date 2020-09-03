package ua.ihor.blablatracker.dto.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TasksAssignedToUser {
    private Long userId;
    private String userName;
    private Map<String, Long> taskNum;
    private Long totalTaskNum;

    public TasksAssignedToUser(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.taskNum = new HashMap<>();
        this.totalTaskNum = 0L;
    }
}
