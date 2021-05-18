package com.openstudies.repositories;

import com.openstudies.model.entities.courses.User2Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface User2CoursesRepository extends JpaRepository<User2Courses, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM user_courses WHERE user_id = ?")
    List<User2Courses> findByUserId(Integer userId);
}
