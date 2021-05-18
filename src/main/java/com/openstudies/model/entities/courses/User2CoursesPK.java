package com.openstudies.model.entities.courses;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class User2CoursesPK implements Serializable {


    @Column(name = "user_id")
    private Long userId;


    @Column(name = "course_id")
    private Long courseId;

    public User2CoursesPK() {
    }

    public User2CoursesPK(Long courseId, Long userId) {
        this.courseId = courseId;
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
