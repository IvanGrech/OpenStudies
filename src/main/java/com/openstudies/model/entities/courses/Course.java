package com.openstudies.model.entities.courses;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openstudies.model.entities.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "COURSE")
@Table(name = "COURSE")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TAG")
    @Size(min = 2, max = 32, message = "Название курса должно быть длинее")
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID")
    @JsonIgnore
    private User owner;


    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COURSE_CODE")
    private String courseCode;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_courses",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private Set<User> usersSubscribed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Set<User> getUsersSubscribed() {
        return usersSubscribed;
    }

    public void setUsersSubscribed(Set<User> usersSubscribed) {
        this.usersSubscribed = usersSubscribed;
    }

}
