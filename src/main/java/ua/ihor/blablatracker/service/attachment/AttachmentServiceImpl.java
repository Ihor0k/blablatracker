package ua.ihor.blablatracker.service.attachment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ihor.blablatracker.dto.response.AttachmentDto;
import ua.ihor.blablatracker.entity.Attachment;
import ua.ihor.blablatracker.entity.Task;
import ua.ihor.blablatracker.exception.AttachmentNotFoundException;
import ua.ihor.blablatracker.exception.InternalServerErrorException;
import ua.ihor.blablatracker.repository.AttachmentRepository;
import ua.ihor.blablatracker.repository.TaskRepository;
import ua.ihor.blablatracker.service.DtoConverters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private TaskRepository taskRepository;
    private AttachmentRepository attachmentRepository;
    private FileStorage fileStorage;
    private DtoConverters dtoConverters;

    private final RandomFileNameGenerator fileNameGenerator = new RandomFileNameGenerator();

    @Transactional
    @Override
    public AttachmentDto upload(long taskId, Resource resource) {
        String name = resource.getFilename();
        String id = fileNameGenerator.uniqFileName();
        if (name == null) {
            name = id;
        }
        String mediaType;
        Long size;
        try {
            size = resource.contentLength();
            mediaType = Files.probeContentType(new File(name).toPath());
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
        Task task = taskRepository.getById(taskId);
        Attachment attachment = new Attachment(
                id,
                name,
                mediaType,
                size,
                task
        );
        Attachment savedAttachment = attachmentRepository.save(attachment);
        fileStorage.save(id, resource);
        return dtoConverters.attachmentToDto(savedAttachment);
    }

    @Override
    public Pair<Attachment, Resource> download(long taskId, String id, String name) {
        Attachment attachment = attachmentRepository.getById(id);
        if (attachment.getTask().getId() != taskId && !attachment.getName().equals(name)) {
            throw new AttachmentNotFoundException(id);
        }
        return Pair.of(attachment, fileStorage.download(id));
    }

    private class RandomFileNameGenerator {
        private static final int fileNameLength = 10;
        private static final String fileNameAlphabet = "abcdefghijklmnopqrstuvwxyz0123456789";

        private final Random random = new Random();

        String uniqFileName() {
            String fileName;
            do {
                fileName = randomString();
            } while (fileStorage.isFileExists(fileName));
            return fileName;
        }

        private String randomString() {
            char[] chars = new char[fileNameLength];
            for (int i = 0; i < chars.length; i++) {
                int randomIndex = random.nextInt(fileNameAlphabet.length());
                chars[i] = fileNameAlphabet.charAt(randomIndex);
            }
            return new String(chars);
        }
    }

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setAttachmentRepository(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Autowired
    public void setFileStorage(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @Autowired
    public void setDtoConverters(DtoConverters dtoConverters) {
        this.dtoConverters = dtoConverters;
    }
}
