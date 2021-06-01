package com.openstudies.model.entities.forms;

import com.openstudies.model.entities.User;

import java.util.List;

public class RestUsersAndTasks {

    private User user;
    private List<String> fileNames;
    private Integer grade;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
