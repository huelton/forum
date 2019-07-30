package com.br.project.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.project.model.Topico;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

	//List<Topico> findByCursoNome(String nomeCurso);

	@Query("SELECT t from Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
}
