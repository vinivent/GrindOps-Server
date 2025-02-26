package com.devent.cprogress.controller;

import com.devent.cprogress.model.Achievement;
import com.devent.cprogress.model.Camouflage;
import com.devent.cprogress.model.User.PasswordRequest;
import com.devent.cprogress.model.User.UpdateUserDTO;
import com.devent.cprogress.model.User.User;
import com.devent.cprogress.service.TokenService;
import com.devent.cprogress.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.replace("Bearer ", "");
            String username = tokenService.validateToken(jwtToken);
            User user = userService.getUserByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/checkPassword")
    public ResponseEntity<Boolean> checkPassword(@RequestBody PasswordRequest request) {
        try {
            boolean isValid = userService.isPasswordValid(request.password(), request.id());
            return ResponseEntity.ok(isValid);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO ) {
        try {
            User updatedUser = userService.updateUser(id, updateUserDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{username}/achievements")
    public ResponseEntity<List<Achievement>> getAchievementsByUsername(@PathVariable String username) {
        try {
            List<Achievement> achievements = userService.getAchievementsByUsername(username);
            return new ResponseEntity<>(achievements, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{username}/camouflages")
    public ResponseEntity<List<Camouflage>> getCamouflagesByUsername(@PathVariable String username) {
        try {
            List<Camouflage> camouflages = userService.getCamouflagesByUsername(username);
            return new ResponseEntity<>(camouflages, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
