package com.openstudies.repositories;

import com.openstudies.model.entities.courses.User2Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface User2TaskRepository extends JpaRepository<User2Task, Long> {

    Optional<User2Task> findByUserIdAndTaskId(Integer userId, Long taskId);
}
