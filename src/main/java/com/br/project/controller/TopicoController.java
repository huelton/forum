package com.br.project.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<TopicoDTO> lista(String nomeCurso) {

		if (nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDTO.converter(topicos);
		}else{
			List<Topico> topicos = topicoRepository.carregarPorNomeDoCurso(nomeCurso);
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
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if(optional.isPresent()){
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();			
		
	}

}
