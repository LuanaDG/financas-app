package com.financas.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.financas.entity.Usuario;
import com.financas.exception.RegraNegocioException;
import com.financas.repository.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void validaNovoEmail() {
		
		Assertions.assertDoesNotThrow(() -> {
			
			repository.deleteAll();
			
			service.validarEmail("email@email.com");
			
		});
	}
	
	@Test
	public void lancarErroSeJaHouverEmailCadastrado() {
		
		Assertions.assertThrows(RegraNegocioException.class, () -> {
			
			Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
			repository.save(usuario);
			
			service.validarEmail("email@email.com");
		});
	}
}
