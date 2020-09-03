package ua.ihor.blablatracker.service.attachment;

import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
import ua.ihor.blablatracker.dto.response.AttachmentDto;
import ua.ihor.blablatracker.entity.Attachment;

public interface AttachmentService {
    AttachmentDto upload(long taskId, Resource resource);

    Pair<Attachment, Resource> download(long taskId, String id, String name);
}
