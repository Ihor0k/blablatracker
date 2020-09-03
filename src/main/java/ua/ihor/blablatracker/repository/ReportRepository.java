package ua.ihor.blablatracker.repository;

import org.springframework.data.jpa.domain.Specification;
import ua.ihor.blablatracker.entity.Task;
import ua.ihor.blablatracker.repository.projection.DepartmentStatusTaskNumView;
import ua.ihor.blablatracker.repository.projection.UserStatusTaskNumView;

import java.util.List;

public interface ReportRepository {

    List<DepartmentStatusTaskNumView> getDepartmentStatusTaskNumView(Specification<Task> specification);

    List<UserStatusTaskNumView> getUserStatusTaskNumView(Specification<Task> specification);
}
