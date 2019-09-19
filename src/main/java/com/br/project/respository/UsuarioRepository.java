package com.br.project.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.project.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
}
