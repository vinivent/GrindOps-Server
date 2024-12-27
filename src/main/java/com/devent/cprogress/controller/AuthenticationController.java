package com.devent.cprogress.controller;

import com.devent.cprogress.model.User.AuthenticationDTO;
import com.devent.cprogress.model.User.LoginResponseDTO;
import com.devent.cprogress.model.User.RegisterDTO;
import com.devent.cprogress.model.User.User;
import com.devent.cprogress.repository.UserRepository;
import com.devent.cprogress.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByUsername(data.username()) != null) {
            return ResponseEntity.badRequest().body("Usuario ja existe!");
        }

        if (data.password().length() >= 6) {
            String encryptedPassword = passwordEncoder.encode(data.password());
            User newUser = new User(data.username(), encryptedPassword, data.email(), data.role());
            this.userRepository.save(newUser);

            return ResponseEntity.ok("Usuario cadastrado com sucesso!");
        }
        return ResponseEntity.badRequest().body("A senha deve ter pelo menos 6 caracteres.");
    }
}
