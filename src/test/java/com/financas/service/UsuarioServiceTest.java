package com.financas.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.financas.entity.Usuario;
import com.financas.exception.ErroAutenticacao;
import com.financas.exception.RegraNegocioException;
import com.financas.repository.UsuarioRepository;
import com.financas.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean
	UsuarioServiceImpl service;
	
	@MockBean
	UsuarioRepository repository;
	
	
	@Test
	public void deveSalvarUmUsuario() {
		
		Assertions.assertDoesNotThrow(() -> {
			
			Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
			Usuario usuario = Usuario.builder().id(1l).nome("nome").email("email@email.com").senha("senha").build();
			Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
			
			Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
			
			Assertions.assertNotNull(usuarioSalvo);
			
			assertEquals(usuarioSalvo.getId(), 1l);
			assertEquals(usuarioSalvo.getNome(), "nome");
			assertEquals(usuarioSalvo.getEmail(), "email@email.com");
			assertEquals(usuarioSalvo.getSenha(), "senha");
		});
	}
	
	@Test
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		
		Assertions.assertThrows(RegraNegocioException.class, () -> {
			
			String email = "email@email.com";
			Usuario usuario = Usuario.builder().email(email).build();
			Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
			
			service.salvarUsuario(usuario);
			
			Mockito.verify(repository, Mockito.never()).save(usuario);
		});
	}
	
	@Test
	public void autenticarUsuarioComSucesso() {
		
		Assertions.assertDoesNotThrow(() -> {
			
			String email = "email@email.com";
			String senha = "senha";
			Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
			Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
			
			Usuario result = service.autenticar(email, senha);
			
			//Assertions.assertThat(result).isNotNull();
			Assertions.assertNotNull(result);
			
		});
	}
	
	@Test
	public void lancarErroQuandoNaoEncontarUsuarioCadastradoComOEmailInformado() {

		Assertions.assertThrows(ErroAutenticacao.class, () -> {
			
			Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
			
			service.autenticar("email@email.com", "senha");
		});
	}
	
	@Test
	public void lancarErroQuandoASenhaNaoBater() {
		
		Assertions.assertThrows(ErroAutenticacao.class, () -> {
			
			String senha = "567";
			Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
			Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
			
			service.autenticar("email@email.com", "123");
		});
	}
	
	@Test
	public void validaNovoEmail() {
		
		Assertions.assertDoesNotThrow(() -> {
			
			//repository.deleteAll();
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
			
			service.validarEmail("email@email.com");
			
		});
	}
	
	@Test
	public void lancarErroSeJaHouverEmailCadastrado() {
		
		Assertions.assertThrows(RegraNegocioException.class, () -> {
			
			//Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
			//repository.save(usuario);
			
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
			
			service.validarEmail("email@email.com");
		});
	}
}
