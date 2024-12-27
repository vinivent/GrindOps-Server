package com.devent.cprogress.service;

import com.devent.cprogress.model.Achievement;
import com.devent.cprogress.model.Camouflage;
import com.devent.cprogress.model.User.User;
import com.devent.cprogress.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CRUD
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Usário não encontrado com o ID: " + id));
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID " + user.getId()));

        if (!existingUser.getPassword().equals(user.getPassword())) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }

        return userRepository.save(user);

    }
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Usuário não encontrado com ID " + id);
        }
    }

    // Métodos adicionais

    // Obter conquistas de um usuário pelo username
    public List<Achievement> getAchievementsByUsername(String username) {
        UserDetails userDetails = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com username: " + username));

        if (!(userDetails instanceof User user)) {
            throw new IllegalStateException("O objeto retornado não é do tipo User");
        }

        return user.getAchievements();
    }

    public List<Camouflage> getCamouflagesByUsername(String username) {
        UserDetails userDetails = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com username: " + username));

        if (!(userDetails instanceof User user)) {
            throw new IllegalStateException("O objeto retornado não é do tipo User");
        }

        return user.getCamouflages();
    }
}
