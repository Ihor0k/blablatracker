package ua.ihor.blablatracker.service.attachment;

import org.springframework.core.io.Resource;

public interface FileStorage {
    void save(String fileName, Resource resource);

    boolean isFileExists(String fileName);

    Resource download(String fileName);
}
