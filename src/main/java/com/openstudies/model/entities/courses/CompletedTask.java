package com.openstudies.model.entities.courses;


import com.openstudies.model.entities.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity(name = "COMPLETED_TASK")
@Table(name = "COMPLETED_TASK")
public class CompletedTask {

    @Id
    @ManyToOne
    @JoinColumn(name = "ID")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "GRADE")
    private int grade;

    @Column(name = "COMPLETED")
    private boolean completed;

    @OneToMany(mappedBy = "COMMENT")
    private List<Comment> comments;

    @Column(name = "FILE_LOCATIONS")
    private String fileLocations;


    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getFileLocations() {
        return fileLocations;
    }

    public void setFileLocations(String fileLocations) {
        this.fileLocations = fileLocations;
    }
}
