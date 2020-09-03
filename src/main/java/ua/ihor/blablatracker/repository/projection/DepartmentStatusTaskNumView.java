package ua.ihor.blablatracker.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.ihor.blablatracker.entity.Task;

@Data
@AllArgsConstructor
public class DepartmentStatusTaskNumView {
    private Long departmentId;
    private String departmentName;
    private Task.Status status;
    private Long taskNum;
}
