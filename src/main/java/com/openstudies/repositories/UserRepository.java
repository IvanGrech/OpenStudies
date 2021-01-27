package com.openstudies.repositories;

import com.openstudies.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    User findByEmail(String email);

    User findById(Integer id);

    void deleteById(Integer id);
}
