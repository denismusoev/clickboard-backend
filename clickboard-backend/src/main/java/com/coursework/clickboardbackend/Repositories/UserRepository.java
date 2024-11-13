package com.coursework.clickboardbackend.Repositories;

import com.coursework.clickboardbackend.Models.Database.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
    Optional<User> findByVkId(int vkId);
}
