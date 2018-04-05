package com.algamoney.api.repository.reactive;

import com.algamoney.api.model.Categoria;
import com.algamoney.api.repository.CategoriaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class CategoriaRepositoryReactiveTest extends BaseReactiveRepositoryTest {

    @Autowired
    private CategoriaRepositoryReactive categoriaRepositoryReactive;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void deveSalvarNovaCategoria() throws Exception {
        Categoria categoria = new Categoria(null, "Nova categoria");

        Mono<Categoria> categoriaMono = categoriaRepositoryReactive.save(categoria);

        StepVerifier.create(categoriaMono)
                .assertNext(c -> {
                    Assert.assertEquals(c.getNome(), categoria.getNome());
                    Assert.assertNotNull(c.getCodigo()); })
                .verifyComplete();
    }

    @Test
    public void deveSalvarListaNovasCategorias() throws Exception {
        Categoria categoria = new Categoria(null, "Nova categoria 1");
        Categoria categoria2 = new Categoria(null, "Nova categoria 2");

        List<Categoria> categorias = Arrays.asList(categoria, categoria2);

        Flux<Categoria> categoriasFlux = categoriaRepositoryReactive.saveAll(categorias);

        StepVerifier.create(categoriasFlux, 1)
                .assertNext(c -> {
                    Assert.assertEquals(c.getNome(), categoria.getNome());
                    Assert.assertNotNull(c.getCodigo()); })
                .thenRequest(1)
                .assertNext(c -> {
                    Assert.assertEquals(c.getNome(), categoria2.getNome());
                    Assert.assertNotNull(c.getCodigo()); })
                .verifyComplete();
    }

    @Test
    public void deveSalvarFluxNovasCategorias() throws Exception {
        Categoria categoria = new Categoria(null, "Nova categoria 1");
        Categoria categoria2 = new Categoria(null, "Nova categoria 2");

        Flux<Categoria> categorias = Flux.just(categoria, categoria2);

        Flux<Categoria> categoriasFlux = categoriaRepositoryReactive.saveAll(categorias);

        StepVerifier.create(categoriasFlux, 1)
                .assertNext(c -> {
                    Assert.assertEquals(c.getNome(), categoria.getNome());
                    Assert.assertNotNull(c.getCodigo()); })
                .thenRequest(1)
                .assertNext(c -> {
                    Assert.assertEquals(c.getNome(), categoria2.getNome());
                    Assert.assertNotNull(c.getCodigo()); })
                .verifyComplete();
    }

    @Test
    public void deveBuscarCategoria() throws Exception {
        Categoria categoria = new Categoria(null, "Nova categoria 1");

        Mono<Categoria> categoriaMono = categoriaRepositoryReactive.save(categoria)
                .flatMap(c -> categoriaRepositoryReactive.findById(c.getCodigo()));

        StepVerifier.create(categoriaMono)
                .assertNext(c -> {
                    Assert.assertEquals(c.getNome(), categoria.getNome());
                    Assert.assertNotNull(c.getCodigo()); })
                .verifyComplete();


    }

    @Test
    public void deveVerificarSeExisteCategoriaPorId() throws Exception {
        Categoria categoria = new Categoria(null, "Nova categoria 1");

        Mono<Boolean> categoriaMono = categoriaRepositoryReactive.save(categoria)
                .flatMap(c -> categoriaRepositoryReactive.existsById(c.getCodigo()));

        StepVerifier.create(categoriaMono)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    public void deveVerificarSeExisteCategoriaPorIdQuandoNaoExistir() throws Exception {

        Mono<Boolean> categoriaMono =  categoriaRepositoryReactive.existsById(100L);

        StepVerifier.create(categoriaMono)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    public void deveVerificarSeExisteCategoriaPorIdPublisher() throws Exception {
        Categoria categoria = new Categoria(null, "Nova categoria 1");

        Mono<Boolean> categoriaMono = categoriaRepositoryReactive.save(categoria)
                .map(c -> Mono.just(c.getCodigo()))
                .flatMap(codigo -> categoriaRepositoryReactive.existsById(codigo));

        StepVerifier.create(categoriaMono)
                .expectNext(true)
                .verifyComplete();
    }


}