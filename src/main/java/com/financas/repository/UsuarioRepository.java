package com.financas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financas.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	boolean existsByEmail(String email);
	
	Optional<Usuario> findByEmail(String email); 
}
