package ua.ihor.blablatracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDto {
    private String name;
    private String mediaType;
    private Long size;
    private String url;
}
