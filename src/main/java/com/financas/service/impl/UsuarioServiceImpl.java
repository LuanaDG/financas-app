package com.financas.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financas.entity.Usuario;
import com.financas.exception.ErroAutenticacao;
import com.financas.exception.RegraNegocioException;
import com.financas.repository.UsuarioRepository;
import com.financas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository repository;

//	public UsuarioServiceImpl(UsuarioRepository repository) {
//		this.repository = repository;
//	}
	
	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario>usuario = repository.findByEmail(email);
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o email informado!");
		}
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida!");
		}
		return usuario.get();
	}
	
	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean jaExiste = repository.existsByEmail(email);
		if(jaExiste) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail!");
		}
		
	}
	
	
}
