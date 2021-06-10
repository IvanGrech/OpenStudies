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

@Service
public class FileServiceImpl implements FileService {


    @Value("${tasks.files.location}")
    String tasksPath;

    @Value("${tasks.user.files.prefix}")
    String userTasksPrefix;



    @Override
    public void saveTaskFile(MultipartFile file, Long taskId) throws IOException {
        String utf8FileName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
        String fileLocation = tasksPath + taskId + "\\" + utf8FileName;
        Path path = Paths.get(tasksPath + taskId);
        Files.createDirectories(path);
        File taskFile = new File(fileLocation);
        if (taskFile.createNewFile()) {
            file.transferTo(taskFile);
        }
    }

    @Override
    public void saveUserAnswerTaskFile(MultipartFile file, Long taskId, Integer userId) throws IOException {
        String utf8FileName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
        String fileLocation = tasksPath + userTasksPrefix + taskId + "\\" + userId
                + "\\" + utf8FileName;
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
    public List<String> getTaskFileNamesForSubscribedUser(Long taskId, Integer userId) {
        File folder = new File(tasksPath + userTasksPrefix + taskId + "\\" + userId);
        String[] fileNames = folder.list();
        if (fileNames == null) {
            return new ArrayList<>(0);
        }
        return Arrays.asList(fileNames);
    }

    @Override
    public File getTaskAnswerFile(Long taskId, Integer userId, String fileName) {
        return new File(tasksPath + userTasksPrefix + taskId + "\\" + userId
                + "\\" + fileName);
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

    @Override
    public void deleteTaskFile(Long taskId, String fileName) {
        File  fileToDelete = new File(tasksPath + taskId + "\\" + fileName);
        fileToDelete.delete();
    }
}
