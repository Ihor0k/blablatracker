package ua.ihor.blablatracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ihor.blablatracker.dto.request.CreateCommentDto;
import ua.ihor.blablatracker.dto.response.CommentDto;
import ua.ihor.blablatracker.entity.Comment;
import ua.ihor.blablatracker.entity.Task;
import ua.ihor.blablatracker.entity.User;
import ua.ihor.blablatracker.exception.EntityNotFoundException;
import ua.ihor.blablatracker.repository.CommentRepository;
import ua.ihor.blablatracker.repository.TaskRepository;
import ua.ihor.blablatracker.repository.UserRepository;

import java.util.Date;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private DtoConverters dtoConverters;

    @Override
    public CommentDto createComment(long taskId, CreateCommentDto createCommentDto) {
        Task task = taskRepository.getById(taskId);

        Long createdByUserId = createCommentDto.getCreatedByUserId();
        User createdByUser = createdByUserId != null ? userRepository.getById(createdByUserId) : null;

        Comment comment = new Comment(
                null,
                createCommentDto.getText(),
                createdByUser,
                new Date(),
                task
        );
        Comment createdComment = commentRepository.save(comment);
        return dtoConverters.commentToDto(createdComment);
    }

    @Override
    public void deleteComment(long taskId, long commentId) {
        Comment comment = commentRepository.getById(commentId);
        if (!(comment.getTask().getId() == taskId)) {
            throw new EntityNotFoundException("Task " + taskId + " doesn't have comment with id " + commentId);
        }
        commentRepository.delete(comment);
    }

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setDtoConverters(DtoConverters dtoConverters) {
        this.dtoConverters = dtoConverters;
    }
}
