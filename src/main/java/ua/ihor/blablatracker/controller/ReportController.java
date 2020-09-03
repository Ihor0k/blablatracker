package ua.ihor.blablatracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.ihor.blablatracker.TaskSpecification;
import ua.ihor.blablatracker.dto.response.TasksAssignedToUser;
import ua.ihor.blablatracker.dto.response.TasksByDepartment;
import ua.ihor.blablatracker.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private ReportService reportService;

    @GetMapping("/tasksByDepartment")
    public ResponseEntity<List<TasksByDepartment>> tasksByDepartment(TaskSpecification taskSpecification) {
        return ResponseEntity
                .ok(reportService.tasksByDepartment(taskSpecification));
    }

    @GetMapping("/tasksAssignedToUser")
    public ResponseEntity<List<TasksAssignedToUser>> tasksAssignedToUser(TaskSpecification taskSpecification) {
        return ResponseEntity
                .ok(reportService.tasksAssignedToUser(taskSpecification));
    }

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
}
