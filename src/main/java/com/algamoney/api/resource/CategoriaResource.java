package com.algamoney.api.resource;

import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.model.Categoria;
import com.algamoney.api.repository.reactive.CategoriaRepositoryReactive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepositoryReactive categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")// and #oauth2.hasScope('read')")
	public Flux<Categoria> listar() {
		return categoriaRepository.findAll();
	}

	@PostMapping
//	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')")// and #oauth2.hasScope('write')")
	public Mono<ResponseEntity<Categoria>> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Mono<Categoria> categoriaSalva = categoriaRepository.save(categoria);
		return categoriaSalva.doOnNext(c -> {
			publisher.publishEvent(new RecursoCriadoEvent(this, response, c.getCodigo()));})
		.map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c)).log();
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
	public Mono<ResponseEntity<Categoria>> buscarPeloCodigo(@PathVariable Long codigo) {
		 return categoriaRepository.findById(codigo)
		 	.map(ResponseEntity::ok)
		 	.switchIfEmpty(Mono.just(ResponseEntity.notFound().build())).log();
	}
	
}
