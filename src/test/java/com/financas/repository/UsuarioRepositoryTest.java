package com.financas.repository;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.financas.entity.Usuario;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void verificaAExistenciaDeUmEmail() {
	
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		//repository.save(usuario);
		
		boolean result = repository.existsByEmail("usuario@email.com");
		
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void retornarFalsoSeNaoHouverUsuarioCadastradoComEmail() {
	
		//repository.deleteAll();
		
		boolean result = repository.existsByEmail("usuario@email.com");
		
		Assertions.assertThat(result).isFalse();		
	}
	
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		Usuario usuario = criarUsuario();
		
		Usuario usuarioSalvo = repository.save(usuario);
		
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	@Test
	public void buscarUsuarioPorEmail() {
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
		
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	@Test
	public void deveRetornarVazioAoBuscarPorEmailQuandoNaoExisteNaBase() {
		
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
		
		Assertions.assertThat(result.isPresent()).isFalse();
	}
	
	public static Usuario criarUsuario() {
		return Usuario
				.builder()
				.nome("usuario")
				.email("usuario@email.com")
				.senha("senha")
				.build();
	}
	
}
