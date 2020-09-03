package ua.ihor.blablatracker.dto.request;

import lombok.Data;

@Data
public class CreateCommentDto {
    private String text;
    private Long createdByUserId;
}
