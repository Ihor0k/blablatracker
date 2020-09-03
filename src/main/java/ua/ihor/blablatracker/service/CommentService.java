package ua.ihor.blablatracker.service;

import ua.ihor.blablatracker.dto.request.CreateCommentDto;
import ua.ihor.blablatracker.dto.response.CommentDto;

public interface CommentService {

    CommentDto createComment(long taskId, CreateCommentDto comment);

    void deleteComment(long taskId, long commentId);
}
