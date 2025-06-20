package com.baobao.shop.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baobao.shop.model.Usuario;
import com.baobao.shop.security.JwtService;
import com.baobao.shop.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        usuarioService.register(username, password, role);
        return "User registered";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        Optional<Usuario> usuario = usuarioService.authenticate(username, password);
        if (usuario.isPresent()) {
            String token = jwtService.generateToken(username, usuario.get().getRole());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public String logout() {
        return "Logout is handled client-side with JWT";
    }
}