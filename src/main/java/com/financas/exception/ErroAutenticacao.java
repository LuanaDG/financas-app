package com.financas.exception;

public class ErroAutenticacao extends RuntimeException{
	
	private static final long serialVersionUID = -4454742601624685631L;
	

	public ErroAutenticacao(String mensagem) {
		super(mensagem);
	}

}
