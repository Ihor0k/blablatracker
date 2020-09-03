package ua.ihor.blablatracker;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import ua.ihor.blablatracker.entity.Task;
import ua.ihor.blablatracker.service.DtoConverters;

import javax.persistence.criteria.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TaskSpecification implements Specification<Task> {

    private Long departmentId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date toDate;

    private String status;

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (departmentId != null) {
            Path<Long> departmentIdPath = root.get("assignedTo").get("department").get("id");
            Predicate departmentIdPredicate = criteriaBuilder.equal(departmentIdPath, departmentId);
            predicates.add(departmentIdPredicate);
        }
        if (fromDate != null) {
            Date adjustedDate = Date.from(fromDate.toInstant().truncatedTo(ChronoUnit.DAYS));
            Path<Date> createdAtPath = root.get("createdAt");
            Predicate fromDatePredicate = criteriaBuilder.greaterThanOrEqualTo(createdAtPath, adjustedDate);
            predicates.add(fromDatePredicate);
        }

        if (toDate != null) {
            // Add one day to match the end of a day of toDate
            Date adjustedDate = Date.from(toDate.toInstant().plus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS));
            Path<Date> createdAtPath = root.get("createdAt");
            Predicate toDatePredicate = criteriaBuilder.lessThan(createdAtPath, adjustedDate);
            predicates.add(toDatePredicate);
        }
        if (status != null) {
            Path<Task.Status> statusPath = root.get("status");
            Predicate statusPredicate = criteriaBuilder.equal(statusPath, DtoConverters.stringToStatus(status));
            predicates.add(statusPredicate);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
