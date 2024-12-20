package com.devent.cprogress.repository;

import com.devent.cprogress.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
