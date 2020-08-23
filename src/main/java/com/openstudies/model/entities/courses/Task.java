package com.openstudies.model.entities.courses;


import javax.persistence.*;
import java.util.List;

@Entity(name = "TASK")
@Table(name = "TASK")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FILE_LOCATIONS")
    private String filesLocations;

    @OneToMany(fetch = FetchType.EAGER)
    private List<CompletedTask> completedTask;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilesLocations() {
        return filesLocations;
    }

    public void setFilesLocations(String filesLocations) {
        this.filesLocations = filesLocations;
    }

    public List<CompletedTask> getCompletedTask() {
        return completedTask;
    }

    public void setCompletedTask(List<CompletedTask> completedTask) {
        this.completedTask = completedTask;
    }
}
