package ua.ihor.blablatracker.dto.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TasksByDepartment {
    private Long departmentId;
    private String departmentName;
    private Map<String, Long> taskNum;
    private Long totalTaskNum;

    public TasksByDepartment(Long departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.taskNum = new HashMap<>();
        this.totalTaskNum = 0L;
    }
}
