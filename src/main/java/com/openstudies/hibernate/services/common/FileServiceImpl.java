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

    @Value("${tasks.user.files.prefix}")
    String userTasksPrefix;



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
    public void saveUserAnswerTaskFile(MultipartFile file, Long taskId, Integer userId) throws IOException {
        String fileLocation = tasksPath + userTasksPrefix + taskId + "\\" + userId
                + "\\" + file.getOriginalFilename();
        Path path = Paths.get(tasksPath + userTasksPrefix + taskId + "\\" + userId);
        Files.createDirectories(path);
        File taskFile = new File(fileLocation);
        if (taskFile.createNewFile()) {
            file.transferTo(taskFile);
        }
    }

    @Override
    public List<String> getTaskFileNames(Long taskId) {
        File folder = new File(tasksPath + taskId);
        String[] fileNames = folder.list();
        if (fileNames == null) {
            return new ArrayList<>(0);
        }
        return Arrays.asList(fileNames);
    }

    @Override
    public File getTaskFile(Long taskId, String fileName) {
        return new File(tasksPath + taskId + "\\" + fileName);
    }

    @Override
    public void deleteTaskFiles(Long taskId) {
        File folder = new File(tasksPath + taskId);
        File[] filePaths = folder.listFiles();
        if (filePaths != null) {
            for (File file : filePaths) {
               file.delete();
            }
        }
    }
}
