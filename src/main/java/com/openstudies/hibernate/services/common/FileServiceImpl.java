package com.openstudies.hibernate.services.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {


    @Value("${tasks.files.location}")
    String tasksPath;


    @Override
    public void saveTaskFile(MultipartFile file, Long taskId) throws IOException {
        String fileLocation = tasksPath + taskId + "\\" + file.getOriginalFilename();
        Path path = Paths.get(tasksPath + taskId);
        Files.createDirectories(path);
        File taskFile = new File(fileLocation);
        if (taskFile.createNewFile()) {
            file.transferTo(taskFile);
        }
    }

    @Override
    public List<String> getTaskFileNames(Long taskId) {
        File folder = new File(tasksPath + taskId);
        String [] fileNames = folder.list();
        if(fileNames == null) {
            return new ArrayList<>(0);
        }
        return Arrays.asList(fileNames);
    }
}
