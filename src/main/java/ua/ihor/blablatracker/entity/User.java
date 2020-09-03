package ua.ihor.blablatracker.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Department department;
    @OneToMany
    @JoinColumn(name = "assignee_id")
    private List<Task> tasks;
}
