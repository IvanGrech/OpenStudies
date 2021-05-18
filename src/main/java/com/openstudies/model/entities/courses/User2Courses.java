package com.openstudies.model.entities.courses;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "user_courses")
@Table(name = "user_courses")
public class User2Courses {

    @EmbeddedId
    private User2CoursesPK user2CoursesPK;


    public User2CoursesPK getUser2CoursesPK() {
        return user2CoursesPK;
    }

    public void setUser2CoursesPK(User2CoursesPK user2CoursesPK) {
        this.user2CoursesPK = user2CoursesPK;
    }
}
