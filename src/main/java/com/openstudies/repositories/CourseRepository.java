package com.openstudies.repositories;

import com.openstudies.model.entities.courses.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByOwnerId(Integer ownerId);

    Course findByCourseCode(String courseCode);
}
