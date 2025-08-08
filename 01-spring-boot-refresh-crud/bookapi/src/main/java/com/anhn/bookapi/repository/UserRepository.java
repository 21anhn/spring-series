package com.anhn.bookapi.repository;

import com.anhn.bookapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
