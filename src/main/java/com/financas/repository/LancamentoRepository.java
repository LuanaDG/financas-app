package com.financas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financas.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
