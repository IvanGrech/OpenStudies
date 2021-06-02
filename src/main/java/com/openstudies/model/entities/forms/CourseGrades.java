package com.openstudies.model.entities.forms;

import java.util.List;

public class CourseGrades {
    private String courseName;
    private List<String> taskNameList;
    private List<Integer> gradeList;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<String> getTaskNameList() {
        return taskNameList;
    }

    public void setTaskNameList(List<String> taskNameList) {
        this.taskNameList = taskNameList;
    }

    public List<Integer> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Integer> gradeList) {
        this.gradeList = gradeList;
    }
}
