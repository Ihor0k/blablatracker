package ua.ihor.blablatracker.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    @JoinColumn(name = "department_id")
    private List<User> users;

    public List<User> getUsers() {
        if (users == null) {
            return Collections.emptyList();
        }
        return users;
    }
}
