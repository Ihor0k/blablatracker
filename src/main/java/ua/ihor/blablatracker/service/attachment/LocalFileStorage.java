package ua.ihor.blablatracker.service.attachment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import ua.ihor.blablatracker.exception.InternalServerErrorException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class LocalFileStorage implements FileStorage {
    private final File dir;

    public LocalFileStorage(@Value("${local.storage.dir}") String localStorageDir) {
        this.dir = new File(localStorageDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void save(String fileName, Resource resource) {
        File file = new File(dir, fileName);
        try (
                InputStream inputStream = resource.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(file)
        ) {
            FileCopyUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public boolean isFileExists(String fileName) {
        return new File(dir, fileName).exists();
    }

    @Override
    public Resource download(String fileName) {
        return new FileSystemResource(new File(dir, fileName));
    }

}
