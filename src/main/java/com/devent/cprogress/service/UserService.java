package com.devent.cprogress.service;

import com.devent.cprogress.model.Achievement;
import com.devent.cprogress.model.Camouflage;
import com.devent.cprogress.model.User.UpdateUserDTO;
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

    public User updateUser(Long id, UpdateUserDTO updateUserDTO) {
        // Busca o usuário existente
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID " + id));

        // Atualiza o username (se fornecido e diferente do atual)
        if (updateUserDTO.username() != null && !updateUserDTO.username().equals(existingUser.getUsername())) {
            // Verifica se o novo username já está em uso
            boolean usernameExists = userRepository.findUserByUsername(updateUserDTO.username()).isPresent();
            if (usernameExists) {
                throw new IllegalArgumentException("O nome de usuário já está em uso.");
            }
            existingUser.setUsername(updateUserDTO.username());
        }

        // Atualiza a senha (se fornecida)
        if (updateUserDTO.password() != null && !updateUserDTO.password().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updateUserDTO.password());
            existingUser.setPassword(encodedPassword);
        }

        // Atualiza o email (se fornecido)
        if (updateUserDTO.email() != null) {
            existingUser.setEmail(updateUserDTO.email());
        }

        // Salva as alterações no banco de dados
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Usuário não encontrado com ID " + id);
        }
    }

    // Métodos adicionais
    public boolean isPasswordValid (String password, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID " + id));

        return passwordEncoder.matches(password, user.getPassword());
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    // Obter conquistas de um usuário pelo username
    public List<Achievement> getAchievementsByUsername(String username) {
        UserDetails userDetails = userRepository.findByUsername(username);

        if (!(userDetails instanceof User user)) {
            throw new IllegalStateException("O objeto retornado não é do tipo User");
        }

        return user.getAchievements();
    }

    public List<Camouflage> getCamouflagesByUsername(String username) {
        UserDetails userDetails = userRepository.findByUsername(username);

        if (!(userDetails instanceof User user)) {
            throw new IllegalStateException("O objeto retornado não é do tipo User");
        }

        return user.getCamouflages();
    }
}
