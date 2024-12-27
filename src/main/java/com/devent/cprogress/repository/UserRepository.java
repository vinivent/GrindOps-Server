package com.devent.cprogress.repository;

import com.devent.cprogress.model.Achievement;
import com.devent.cprogress.model.Camouflage;
import com.devent.cprogress.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);

    List<Achievement> findAchievementsByUsername(String username);

    List<Camouflage> findCamouflagesByUsername(String username);
}
