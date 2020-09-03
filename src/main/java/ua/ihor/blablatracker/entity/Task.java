package ua.ihor.blablatracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Type(type = "text")
    private String description;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User createdBy;
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignedTo;
    private Status status;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "task_id")
    @OrderBy("createdAt")
    private List<Comment> comments;
    @OneToMany
    @JoinColumn(name = "task_id")
    private List<Attachment> attachments;

    public List<Comment> getComments() {
        if (comments == null) {
            return Collections.emptyList();
        }
        return comments;
    }

    public List<Attachment> getAttachments() {
        if (attachments == null) {
            return Collections.emptyList();
        }
        return attachments;
    }

    public enum Status {
        // Do not change the order. It's stored in DB by it's ordinal number
        TODO, IN_PROGRESS, DONE
    }
}
