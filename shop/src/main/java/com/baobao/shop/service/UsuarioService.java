package com.baobao.shop.service;

import com.baobao.shop.model.Usuario;
import com.baobao.shop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario register(String username, String password, String role) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRole(role);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> authenticate(String username, String password) {
        return usuarioRepository.findByUsername(username)
                .filter(usuario -> passwordEncoder.matches(password, usuario.getPassword()));
    }
}