package com.openstudies.model.entities.courses;


import com.openstudies.model.entities.User;


import javax.persistence.*;

@Entity(name = "COMMENT")
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MESSAGE")
    private String message;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    private CompletedTask completedTask;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CompletedTask getCompletedTask() {
        return completedTask;
    }

    public void setCompletedTask(CompletedTask completedTask) {
        this.completedTask = completedTask;
    }
}
