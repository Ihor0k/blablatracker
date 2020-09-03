package ua.ihor.blablatracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.ihor.blablatracker.TaskSpecification;
import ua.ihor.blablatracker.dto.request.CreateCommentDto;
import ua.ihor.blablatracker.dto.request.CreateTaskDto;
import ua.ihor.blablatracker.dto.request.UpdateTaskDto;
import ua.ihor.blablatracker.dto.response.AttachmentDto;
import ua.ihor.blablatracker.dto.response.CommentDto;
import ua.ihor.blablatracker.dto.response.TaskDetailsDto;
import ua.ihor.blablatracker.dto.response.TaskDto;
import ua.ihor.blablatracker.entity.Attachment;
import ua.ihor.blablatracker.service.CommentService;
import ua.ihor.blablatracker.service.TaskService;
import ua.ihor.blablatracker.service.attachment.AttachmentService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;
    private CommentService commentService;
    private AttachmentService attachmentService;

    @GetMapping
    public ResponseEntity<Page<TaskDto>> getTasks(TaskSpecification taskSpecification,
                                                  Pageable pageable) {
        Sort.Order orderByCreatedAt = pageable.getSort().getOrderFor("createdAt");
        Sort sort = orderByCreatedAt != null ? Sort.by(orderByCreatedAt) : Sort.unsorted();
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return ResponseEntity
                .ok(taskService.getTasks(taskSpecification, pageable));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDetailsDto> getTaskDetails(@PathVariable long taskId) {
        return ResponseEntity
                .ok(taskService.getTaskDetails(taskId));
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskDto createTaskDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.createTask(createTaskDto));
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable long taskId,
                                              @RequestBody UpdateTaskDto updateTaskDto) {
        return ResponseEntity
                .ok(taskService.updateTask(taskId, updateTaskDto));
    }

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long taskId,
                                                    @RequestBody CreateCommentDto createCommentDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(taskId, createCommentDto));
    }

    @DeleteMapping("/{taskId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable long taskId,
                                           @PathVariable long commentId) {
        commentService.deleteComment(taskId, commentId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/{taskId}/attachments")
    public ResponseEntity<AttachmentDto> uploadAttachment(@PathVariable long taskId,
                                                          @RequestParam("file") MultipartFile file) {
        return ResponseEntity
                .ok(attachmentService.upload(taskId, file.getResource()));
    }

    @GetMapping("/{taskId}/attachments/{fileId}/{fileName}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable long taskId,
                                                       @PathVariable String fileId,
                                                       @PathVariable String fileName) {
        Pair<Attachment, Resource> attachmentAndResource = attachmentService.download(taskId, fileId, fileName);
        Attachment attachmentDto = attachmentAndResource.getFirst();
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(attachmentDto.getMediaType()))
                .contentLength(attachmentDto.getSize())
                .body(attachmentAndResource.getSecond());
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }
}
