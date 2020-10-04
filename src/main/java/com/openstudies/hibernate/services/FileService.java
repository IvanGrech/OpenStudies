package com.openstudies.hibernate.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    public void saveTaskFile(MultipartFile file, Long taskId) throws IOException;

}
