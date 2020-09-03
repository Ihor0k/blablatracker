package ua.ihor.blablatracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Type(type = "text")
    private String text;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User createdBy;
    private Date createdAt;
    @ManyToOne
    private Task task;
}
