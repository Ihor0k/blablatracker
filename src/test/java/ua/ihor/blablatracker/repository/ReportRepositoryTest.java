package ua.ihor.blablatracker.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.ihor.blablatracker.TaskSpecification;
import ua.ihor.blablatracker.entity.Task;
import ua.ihor.blablatracker.repository.projection.DepartmentStatusTaskNumView;
import ua.ihor.blablatracker.repository.projection.UserStatusTaskNumView;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/test.properties")
public class ReportRepositoryTest {
    @Autowired
    private ReportRepository reportRepository;

    @Test
    public void departmentStatusTaskNum() {
        DepartmentStatusTaskNumView[] expected = {
                new DepartmentStatusTaskNumView(1L, "Frontend team", Task.Status.TODO, 1L),
                new DepartmentStatusTaskNumView(1L, "Frontend team", Task.Status.IN_PROGRESS, 2L),
                new DepartmentStatusTaskNumView(1L, "Frontend team", Task.Status.DONE, 3L),
                new DepartmentStatusTaskNumView(2L, "Backend team", Task.Status.TODO, 3L),
                new DepartmentStatusTaskNumView(2L, "Backend team", Task.Status.IN_PROGRESS, 1L),
                new DepartmentStatusTaskNumView(2L, "Backend team", Task.Status.DONE, 2L)
        };
        List<DepartmentStatusTaskNumView> actual = reportRepository.getDepartmentStatusTaskNumView(new TaskSpecification());
        assertThat(actual).containsExactlyInAnyOrder(expected);
    }

    @Test
    public void userStatusTaskNum() {
        UserStatusTaskNumView[] expected = {
                new UserStatusTaskNumView(1L, "Jane Doe", Task.Status.DONE, 1L),
                new UserStatusTaskNumView(2L, "Tom Smith", Task.Status.TODO, 1L),
                new UserStatusTaskNumView(2L, "Tom Smith", Task.Status.IN_PROGRESS, 1L),
                new UserStatusTaskNumView(2L, "Tom Smith", Task.Status.DONE, 2L),
                new UserStatusTaskNumView(3L, "Hilary Ouse", Task.Status.IN_PROGRESS, 1L),
                new UserStatusTaskNumView(4L, "Norman Gordon", Task.Status.DONE, 1L),
                new UserStatusTaskNumView(5L, "Richard Tea", Task.Status.TODO, 2L),
                new UserStatusTaskNumView(6L, "Miles Tone", Task.Status.TODO, 1L),
                new UserStatusTaskNumView(6L, "Miles Tone", Task.Status.DONE, 1L),
                new UserStatusTaskNumView(7L, "Desmond Eagle", Task.Status.IN_PROGRESS, 1L)
        };
        List<UserStatusTaskNumView> actual = reportRepository.getUserStatusTaskNumView(new TaskSpecification());
        assertThat(actual).containsExactlyInAnyOrder(expected);
    }

    @Test
    public void departmentStatusTaskNumFromDate() {
        DepartmentStatusTaskNumView[] expected = {
                new DepartmentStatusTaskNumView(1L, "Frontend team", Task.Status.IN_PROGRESS, 1L),
                new DepartmentStatusTaskNumView(2L, "Backend team", Task.Status.TODO, 2L)
        };
        TaskSpecification specification = new TaskSpecification();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.SEPTEMBER, 3);
        specification.setFromDate(calendar.getTime());
        List<DepartmentStatusTaskNumView> actual = reportRepository.getDepartmentStatusTaskNumView(specification);
        assertThat(actual).containsExactlyInAnyOrder(expected);
    }


    @Test
    public void departmentStatusTaskNumToDate() {
        DepartmentStatusTaskNumView[] expected = {
                new DepartmentStatusTaskNumView(1L, "Frontend team", Task.Status.IN_PROGRESS, 1L),
                new DepartmentStatusTaskNumView(1L, "Frontend team", Task.Status.DONE, 2L),
                new DepartmentStatusTaskNumView(2L, "Backend team", Task.Status.TODO, 1L),
                new DepartmentStatusTaskNumView(2L, "Backend team", Task.Status.IN_PROGRESS, 1L)
        };
        TaskSpecification specification = new TaskSpecification();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.SEPTEMBER, 1);
        specification.setToDate(calendar.getTime());
        List<DepartmentStatusTaskNumView> actual = reportRepository.getDepartmentStatusTaskNumView(specification);
        assertThat(actual).containsExactlyInAnyOrder(expected);
    }
}
