package ua.ihor.blablatracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ihor.blablatracker.TaskSpecification;
import ua.ihor.blablatracker.entity.Task;
import ua.ihor.blablatracker.dto.response.TasksAssignedToUser;
import ua.ihor.blablatracker.dto.response.TasksByDepartment;
import ua.ihor.blablatracker.repository.ReportRepository;
import ua.ihor.blablatracker.repository.projection.DepartmentStatusTaskNumView;
import ua.ihor.blablatracker.repository.projection.UserStatusTaskNumView;

import java.util.*;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    private ReportRepository reportRepository;

    @Override
    public List<TasksByDepartment> tasksByDepartment(Specification<Task> taskSpecification) {
        List<DepartmentStatusTaskNumView> records = reportRepository.getDepartmentStatusTaskNumView(taskSpecification);
        Map<Long, TasksByDepartment> tasksByDepartmentMap = new HashMap<>();
        for (DepartmentStatusTaskNumView record : records) {
            Long departmentId = record.getDepartmentId();
            TasksByDepartment tasksByDepartment = tasksByDepartmentMap
                    .computeIfAbsent(departmentId, (__) -> new TasksByDepartment(departmentId, record.getDepartmentName()));
            String status = DtoConverters.statusToString(record.getStatus());
            Map<String, Long> taskNumMap = tasksByDepartment.getTaskNum();
            Long taskNum = record.getTaskNum();
            taskNumMap.put(status, taskNum);
            tasksByDepartment.setTotalTaskNum(tasksByDepartment.getTotalTaskNum() + taskNum);
        }
        return new ArrayList<>(tasksByDepartmentMap.values());
    }

    @Override
    public List<TasksAssignedToUser> tasksAssignedToUser(TaskSpecification taskSpecification) {
        List<UserStatusTaskNumView> records = reportRepository.getUserStatusTaskNumView(taskSpecification);
        Map<Long, TasksAssignedToUser> tasksAssignedToUserMap = new HashMap<>();
        for (UserStatusTaskNumView record : records) {
            Long userId = record.getUserId();
            TasksAssignedToUser tasksAssignedToUser = tasksAssignedToUserMap
                    .computeIfAbsent(userId, (__) -> new TasksAssignedToUser(userId, record.getUserName()));
            String status = DtoConverters.statusToString(record.getStatus());
            Map<String, Long> taskNumMap = tasksAssignedToUser.getTaskNum();
            Long taskNum = record.getTaskNum();
            taskNumMap.put(status, taskNum);
            tasksAssignedToUser.setTotalTaskNum(tasksAssignedToUser.getTotalTaskNum() + taskNum);
        }
        return new ArrayList<>(tasksAssignedToUserMap.values());
    }

    @Autowired
    public void setReportRepository(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
