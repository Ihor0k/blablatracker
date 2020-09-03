package ua.ihor.blablatracker.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ua.ihor.blablatracker.entity.Department;
import ua.ihor.blablatracker.entity.Task;
import ua.ihor.blablatracker.entity.User;
import ua.ihor.blablatracker.repository.projection.DepartmentStatusTaskNumView;
import ua.ihor.blablatracker.repository.projection.UserStatusTaskNumView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ReportRepositoryImpl implements ReportRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DepartmentStatusTaskNumView> getDepartmentStatusTaskNumView(Specification<Task> specification) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<DepartmentStatusTaskNumView> query = criteriaBuilder.createQuery(DepartmentStatusTaskNumView.class);

        Root<Task> task = query.from(Task.class);
        Join<Task, User> user = task.join("assignedTo");
        Join<User, Department> department = user.join("department");

        query.groupBy(department.get("id"), task.get("status"));
        query.multiselect(department.get("id"), department.get("name"), task.get("status"), criteriaBuilder.count(task.get("id")));
        query.where(specification.toPredicate(task, query, criteriaBuilder));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<UserStatusTaskNumView> getUserStatusTaskNumView(Specification<Task> specification) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<UserStatusTaskNumView> query = criteriaBuilder.createQuery(UserStatusTaskNumView.class);

        Root<Task> task = query.from(Task.class);
        Join<Task, User> user = task.join("assignedTo");

        query.groupBy(user.get("id"), task.get("status"));
        query.multiselect(user.get("id"), user.get("name"), task.get("status"), criteriaBuilder.count(task.get("id")));
        query.where(specification.toPredicate(task, query, criteriaBuilder));

        return em.createQuery(query).getResultList();
    }
}
