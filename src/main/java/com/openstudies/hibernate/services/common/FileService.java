package com.openstudies.hibernate.services.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {

    void saveTaskFile(MultipartFile file, Long taskId) throws IOException;

    List<String> getTaskFileNames(Long taskId);

    File getTaskFile(Long taskId, String fileName);

    void deleteTaskFiles(Long taskId);

}
