package com.financas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.financas.entity.Lancamento;
import com.financas.entity.enums.StatusLancamento;

public interface LancamentoService {
	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento atualizar(Lancamento lancamento);
	
	void deletar(Lancamento lancamento);
	
	List<Lancamento> buscar(Lancamento lancamentoFiltro);
	
	void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	
	void validar(Lancamento lancamento);
	
	Optional<Lancamento> obterLancamentoPorId(Long id);
	
	BigDecimal obterSaldoPorUsuario(Long id);
}
