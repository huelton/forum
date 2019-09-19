package com.br.project.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.project.controller.dto.DetalhesDoTopicoDTO;
import com.br.project.controller.dto.TopicoDTO;
import com.br.project.controller.form.AtualizacaoTopicoForm;
import com.br.project.controller.form.TopicoForm;
import com.br.project.model.Topico;
import com.br.project.respository.CursoRepository;
import com.br.project.respository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	@Cacheable(value = "listaDeTopicos")
	public Page<TopicoDTO> lista(String nomeCurso, @PageableDefault(sort  = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {

		if (nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDTO.converter(topicos);
		}else{
			Page<Topico> topicos = topicoRepository.carregarPorNomeDoCurso(nomeCurso, paginacao);
			return TopicoDTO.converter(topicos);
		}		
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDTO> detalhar(@PathVariable Long id) {
		
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()){
			return ResponseEntity.ok(new DetalhesDoTopicoDTO(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping
	@Transactional // avisa que a transação precisa ser comitada, necessario em salvar, alterar e excluir
	@CacheEvict(value = "listaDeTopicos", allEntries= true ) // invalidacao de cache
	public ResponseEntity<TopicoDTO>cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}")
				            .buildAndExpand(topico.getId())
				            .toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	@PutMapping("/{id}")
	@Transactional // avisa que a transação precisa ser comitada, necessario em salvar, alterar e excluir
	@CacheEvict(value = "listaDeTopicos", allEntries= true ) // invalidacao de cache
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if(optional.isPresent()){
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDTO(topico));
		}
		
		return ResponseEntity.notFound().build();	
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional // avisa que a transação precisa ser comitada, necessario em salvar, alterar e excluir
	@CacheEvict(value = "listaDeTopicos", allEntries= true ) // invalidacao de cache
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if(optional.isPresent()){
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();			
		
	}

}
