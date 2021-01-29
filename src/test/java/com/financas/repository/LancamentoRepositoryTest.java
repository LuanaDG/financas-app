package com.financas.repository;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.financas.entity.Lancamento;
import com.financas.entity.enums.StatusLancamento;
import com.financas.entity.enums.TipoLancamento;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LancamentoRepositoryTest {

	@Autowired
	LancamentoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void salvarLancamento() {
		Lancamento lancamento = criarLancamento();
		
		lancamento = repository.save(lancamento);
		//Assertions.assertThat(lancamento.getId()).isNotNull();
		assertNotNull(lancamento.getId());
	}
	
	@Test
	public void deletarLancamento() {
		Lancamento lancamento = criarLancamento();
		entityManager.persist(lancamento);
		
		lancamento = entityManager.find(Lancamento.class, lancamento.getId());
		
		repository.delete(lancamento);
		
		Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
		//Assertions.assertThat(lancamentoInexistente).isNull();
		assertNull(lancamentoInexistente);
		
	}
	
	@Test
	public void atualizarLancamento() {
		Lancamento lancamento = criarLancamento();
		entityManager.persist(lancamento);
		
		lancamento.setAno(2018);
		lancamento.setDescricao("Atualizado");
		lancamento.setStatus(StatusLancamento.CANCELADO);
		
		repository.save(lancamento);
		
		Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());
		
		assertEquals(lancamentoAtualizado.getAno(), 2018);
		assertEquals(lancamentoAtualizado.getDescricao(), "Atualizado");
		assertEquals(lancamentoAtualizado.getStatus(), StatusLancamento.CANCELADO);
	}
	
	public void buscarLancamentoPorId() {
		Lancamento lancamento = criarLancamento();
		entityManager.persist(lancamento);
		
		Optional<Lancamento> lancamentoEncontrado = repository.findById(lancamento.getId());
		//Assertions.assertThat(lancamentoEncontrado.isPresent()).isTrue();
		assertTrue(lancamentoEncontrado.isPresent());
	}
	
	
	public static Lancamento criarLancamento() {
		return Lancamento.builder()
				.ano(2019)
				.mes(1)
				.descricao("lançamento qualquer")
				.valor(BigDecimal.valueOf(100))
				.tipo(TipoLancamento.RECEITA)
				.status(StatusLancamento.PENDENTE)
				.dataCadastro(LocalDate.now())
				.build();
	}
}
